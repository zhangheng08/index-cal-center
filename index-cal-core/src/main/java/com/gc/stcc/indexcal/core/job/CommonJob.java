package com.gc.stcc.indexcal.core.job;

import com.alibaba.fastjson.JSONObject;
import com.gc.stcc.indexcal.common.enums.ECalType;
import com.gc.stcc.indexcal.common.model.*;
import com.gc.stcc.indexcal.common.vo.DimensionDefinitionVo;
import com.gc.stcc.indexcal.common.vo.Result;
import com.gc.stcc.indexcal.core.exception.ParamParseException;
import com.gc.stcc.indexcal.core.feign.IndexCalConfigFeignClient;
import com.gc.stcc.indexcal.core.utils.*;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author hsd
 * @Date 2020/8/7 15:40
 * @Version:1.0
 * ------------------------------------
 * @Copyright (C) 2021, 华录高诚
 * @FileName: CommonJob
 * @Author: ZhangHeng
 * @Date: 2021/6/30 15:36
 * @Description:
 * @Version:2.0
 */
@Slf4j
public abstract class CommonJob<Index> {

    /*时间间隔数据集合*/
    protected Map<String, DimensionTimeInterval> intervals;
    /*跨服务读取配置信息*/
    @Autowired
    private IndexCalConfigFeignClient indexCalConfigFeignClient;

    private JdbcTemplate dialect;

    private SparkSession spark;

    private boolean userConcurrent;


    /**
     * @desc 获取 xxl-job 调度任务标识符
     * @author zhangheng
     * @date 2021/7/5
     * @param methodName:
     * @return: java.lang.String
     */
    protected String getHandlerName(String methodName) {

        String handlerName = "";

        try {

            Method method = this.getClass().getDeclaredMethod(methodName, String.class);

            handlerName = method.getAnnotation(XxlJob.class).value();

        } catch (NoSuchMethodException e) {

            e.printStackTrace();
        }

        return handlerName;
    }

    /**
     * @desc 涵盖指标计算所有流程的方法
     * @author zhangheng
     * @date 2021/7/6
     * @param handlerName:
     * @param params:
     * @return: java.util.List<java.util.List<com.gc.stcc.indexcal.common.model.IndexTaxiWorkNumEntity>>
     */

    public List<List<Index>> calculate(Class<Index> tClass,String handlerName, String params, ECalType calType, JdbcTemplate jdbcTemplate) throws Exception {
        dialect = jdbcTemplate;
        //初始化时间间隔维度数据
        intervals = initTimeInterval();
        //参数解释
        Map<String, String> paramsMap = ParamParseUtil.paramParse(params);
        //租户ID
        final String tenantId = paramsMap.get(ParamParseUtil.PARAMS_KEY_TENANT_ID);
        //获取计算任务定义信息实例
        IndexCalHandlerDefineEntity calDefine = getIndexCalHandlerDefineEntity(paramsMap, handlerName);
        //生成指标计算的配置信息：时间维度层次、计算维
        List<CalConfigMap> calConfigMapList = getCalConfigMap(paramsMap, handlerName, calDefine);
        //生成SQL及开始计算：维度补充、补值计算、保存至结果表、生成计算日志
        return getIndexNumEntities(calType, tClass, tenantId, paramsMap, calDefine, calConfigMapList, jdbcTemplate);
    }

    /**
     * @desc 获取计算任务定义信息实例
     * @author zhangheng
     * @date 2021/7/5
     * @param paramsMap:
     * @param handlerName:
     * @return: com.gc.stcc.indexcal.common.model.IndexCalHandlerDefineEntity
     */
    protected IndexCalHandlerDefineEntity getIndexCalHandlerDefineEntity(Map<String, String> paramsMap, String handlerName) throws Exception {

        XxlJobHelper.log("index cal: job definition loading");

        final String indexCode = paramsMap.get(ParamParseUtil.PARAMS_KEY_INDEX_CODE);
        //获取由前端生成计算任务配置信息
        final Result<IndexCalHandlerDefineEntity> result = indexCalConfigFeignClient.fetchCalDefine(indexCode, handlerName);

        IndexCalHandlerDefineEntity calDefine = result.getData();
        // 若无初始计算时间则认为是第一次计算，需要在参数中有首次计算时间，并更新handlerDefinition 任务表
        if(calDefine.getFirstCalDate() == null) {

            XxlJobHelper.log("first calculate , init calculate definition first cal time info.");
            //首次计算，若计算参数中无起始时间，抛异常
            if(!paramsMap.containsKey(ParamParseUtil.PARAMS_KEY_FIRST_CAL_TIME)) {

                String errorStr = "first calculate need param : [first_cal_time]";

                XxlJobHelper.log(errorStr);

                log.error(errorStr);

                throw new ParamParseException(errorStr);
            }

            String firstCalTimeStr = paramsMap.get(ParamParseUtil.PARAMS_KEY_FIRST_CAL_TIME);

            if(!StringUtils.isEmpty(firstCalTimeStr)) {
                //参数中日期格式不标准，需处理成标准格式
                firstCalTimeStr = firstCalTimeStr.replace("_", "-").replace("T", " ");
                //校验日期格式
                if(!ParamParseUtil.isValidDate(firstCalTimeStr)) {

                    String errorStr = "first_cal_time param format error";

                    XxlJobHelper.log(errorStr);

                    log.error(errorStr);

                    throw new ParamParseException(errorStr);
                }
            }

            Date firstCalDate = DateUtil.str2Date(firstCalTimeStr, DateUtil.DEFAULT_DATE_TIME_FORMAT);

            calDefine.setFirstCalDate(firstCalDate);
            //更新计算任务信息，填入初始计算日期
            final Result<Boolean> ret = indexCalConfigFeignClient.updateCalDefinition(JSONObject.toJSONString(calDefine));

            if(!ret.getData()) {

                String errorStr = "set first_cal_time date info error";

                XxlJobHelper.log(errorStr);

                log.error(errorStr);

                throw new ParamParseException(errorStr);
            }
        }

        XxlJobHelper.log("index cal : job definition load finish.");

        return calDefine;
    }

    /**
     * @desc 生成指标计算的配置信息
     * @author zhangheng
     * @date 2021/6/30
     * @param paramsMap:
     * @param handlerName:
     * @param calDefine:
     * @return: java.util.List<com.gc.stcc.indexcal.core.utils.CalConfigMap>
     */
    protected List<CalConfigMap> getCalConfigMap(final Map<String, String> paramsMap, final String handlerName, final IndexCalHandlerDefineEntity calDefine) {

        XxlJobHelper.log("index cal: cal configuration loading ");

        final String indexCode = paramsMap.get(ParamParseUtil.PARAMS_KEY_INDEX_CODE);

        final String calType = paramsMap.get(ParamParseUtil.PARAMS_KEY_CAL_TYPE);

        final String calIntervalDimensions = paramsMap.get(ParamParseUtil.PARAMS_KEY_INTERVAL_CAL_DIMENSIONS);

        final String[] calIntervalArray = calIntervalDimensions.split("_");

        final List<CalConfigMap> calConfigMapList = new ArrayList<>();

        //获取维度配置信息
        final Result<IndexManageDimensionDefinition> indexManageDimensionDefinitionResult = indexCalConfigFeignClient.fetchDimensionDefinition(calDefine.getDimensionDefinitionId());

        final IndexManageDimensionDefinition indexManageDimensionDefinition = indexManageDimensionDefinitionResult.getData();
        //解释维度配置信息
        final DimensionDefinitionVo dimensionDefinitionVo = JSONObject.parseObject(indexManageDimensionDefinition.getDefinitionJson(), DimensionDefinitionVo.class);
        //获取时间维度及计算维度实例
        final List<DimensionDefinitionVo.DimensionLayerInfoBean> dimensionLayerInfo = dimensionDefinitionVo.getDimensionLayerInfo();

        //先从时间间隔维度创建计算配置信息
        // todo "date"
        dimensionLayerInfo.stream().filter(dimensionLayerInfoBean -> "date".equals(dimensionLayerInfoBean.getDimensionType())).forEach(dimensionLayerInfoBean -> {

            //读取时间维度层级配置
            final List<DimensionDefinitionVo.DimensionLayerInfoBean.DimensionGroupBean> dimensionGroup = dimensionLayerInfoBean.getDimensionGroup();
            //根据时间维度层级排序
            dimensionGroup.sort(Comparator.comparingInt(DimensionDefinitionVo.DimensionLayerInfoBean.DimensionGroupBean::getStratum));

            dimensionGroup.forEach(dimensionGroupBean -> {
                //年/季度/月/日/x分钟
                String intervalName = dimensionGroupBean.getDimensionColumn();
                //依据参数中传递的时间维度进一步匹配计算范围
                if(Arrays.stream(calIntervalArray).filter(intervalName::equals).count() > 0) {

                    final String dimensionCode = dimensionGroupBean.getDimensionColumn();

                    /** 事实表中的时间维度字段，必须包含 year、month、day 或 增加 quarter week 且列名必须 与 dwd_dim_time_interval 中 code 值一致 */
                    final DimensionTimeInterval dimensionTimeInterval = intervals.get(dimensionCode);

                    int intervalId = dimensionTimeInterval.getIntervalId();

                    //获取此时间维度下上次保存的计算任务日志
                    final Result<IndexCalHandlerLogEntity> result = indexCalConfigFeignClient.fetchJobLog(indexCode, handlerName, String.valueOf(intervalId));

                    IndexCalHandlerLogEntity indexCalLog = result.getData();

                    // 若无运算记录 则认为是系统第一次进行计算
                    CalConfigMap calMap = null;

                    Date currentDate = new Date();

                    if(indexCalLog == null) {

                        //首次计算以当前日期为结束时间，时间跨度包含的完整时间间隔需要补算
                        calMap = new CalConfigMap(calDefine.getFirstCalDate(), currentDate, String.valueOf(intervalId));
                        calMap.setFirstCalculate(true);

                    } else {
                        //非首次，以上次结束时间为起始时间，增量时间间隔后的日期作为结束时间
                        calMap = new CalConfigMap(indexCalLog.getCalEndDate(), currentDate, String.valueOf(intervalId));
                    }

                    calMap.setIntervalName(intervalName);

                    // 如果计算方式 不为系统计算则 需要修改计算开始 和 结束时间
                    if(!ParamParseUtil.CAL_TYPE_SYSTEM.equalsIgnoreCase(calType)) {

                        Date startCalDateTmp = ParamParseUtil.getDate(paramsMap, ParamParseUtil.PARAMS_KEY_START_CAL_TIME);

                        calMap.setStartDate(startCalDateTmp);

                        calMap.setEndDate(CalConfigMap.getNextDateByIntervalId(startCalDateTmp, String.valueOf(intervalId)));

                        calMap.setResultDate(calMap.getEndDate());
                    }

                    calConfigMapList.add(calMap);
                }
            });
        });

        //todo "calculate"
        dimensionLayerInfo.stream().filter(dimensionLayerInfoBean -> "calculate".equals(dimensionLayerInfoBean.getDimensionType())).forEach(dimensionLayerInfoBean -> {
            //读取计算维度的层级配置,并为计算配置添加其他维度的层级配置
            calConfigMapList.forEach(calConfigMap -> {
                calConfigMap.setDimensionLayerInfoBean(dimensionLayerInfoBean);
            });
        });

        XxlJobHelper.log("index cal: cal configuration loaded：" + calConfigMapList.toString());

        XxlJobHelper.log("index cal: cal configuration load finish");

        return calConfigMapList;
    }

    /**
     * @desc 调用SQL并计算
     * @author zhangheng
     * @date 2021/7/5
     * @param clazz: 指标实体类型 Class，由类泛型统一指定
     * @param tenantId: 租户ID
     * @param paramsMap: 参数列表
     * @param calDefine: 指标任务定义信息
     * @param calConfigMapList: 指标计算维度配置及时间等信息
     * @return: java.util.List<java.util.List<T>>
     */
    protected List<List<Index>> getIndexNumEntities(ECalType calType, Class<Index> clazz, String tenantId,  Map<String, String> paramsMap, IndexCalHandlerDefineEntity calDefine, List<CalConfigMap> calConfigMapList, JdbcTemplate jdbcTemplate) {

        XxlJobHelper.log("index cal definition: fetch index metadata");

        final List<List<Index>> resList = new ArrayList<>();

        final Result<IndexManageIndexForCalEntity> indexManageIndexForCalEntityResult = indexCalConfigFeignClient.fetchIndexManageForCal(calDefine.getIndexId());

        if(indexManageIndexForCalEntityResult.getData() == null || !Boolean.parseBoolean(indexManageIndexForCalEntityResult.getResp_msg())) {
            XxlJobHelper.log("index cal definition: fetch origin table failed ");
            return resList;
        }

        final IndexManageIndexForCalEntity indexForCal = indexManageIndexForCalEntityResult.getData();

        final String indexCode = paramsMap.get(ParamParseUtil.PARAMS_KEY_INDEX_CODE);

        final String handlerName = calDefine.getHandlerName();

        final String ownerId = paramsMap.getOrDefault(ParamParseUtil.PARAMS_KEY_OWNER_ID, "0");

        final String originTable = indexForCal.getOriginTableName();

        final String resultTable = indexForCal.getResultTableName();

        //遍历计算配置信息，每个配置包含一个时间维度和所有计算维度
        calConfigMapList.forEach(calConfigMap -> {
            //时间间隔维度代码
            final String intervalId = calConfigMap.getIntervalId();

            final DimensionDefinitionVo.DimensionLayerInfoBean dimensionLayerInfoBean = calConfigMap.getDimensionLayerInfoBean();

            StringBuffer dimension = new StringBuffer();
            //将所有计算维度值，拼接成 group by 的字段列表
            dimensionLayerInfoBean.getDimensionGroup().forEach(dimensionGroupBean -> {

                if(dimension.length() != 0) {

                    dimension.append(" ,");

                }

                dimension.append(dimensionGroupBean.getDimensionColumn());

            });

            //获取SQL及必要的参数信息
            final CalculateDialect calculateDialect = byPathTimeSupplement(calType, calConfigMap, dimension, originTable, resultTable);

            if(userConcurrent) {
                // todo
            } else {

                List<Map<String, Object>> sqlResultMapList = new ArrayList<>();

                String calResult = null;

                long beginCalTimestamp = System.currentTimeMillis();

                int writeNum = 0;

                try {

                    //执行计算
                    final List<Map<String, Object>> result =  doCalculate(this::beforeDoCalculate, this::afterDoCalculate, calculateDialect, jdbcTemplate);

                    //todo 从维度配置信息中获取所有维度组合，补全维度聚合0值
                    //supplyZeroDimensionData(calConfigMap, result, jdbcTemplate);

                    writeNum = result.size();

                    XxlJobHelper.log("dimension: [{}] sql：[{}]", dimension, calculateDialect);

                    sqlResultMapList.addAll(result);

                    calResult = "SUCCESS";

                } catch (Exception e) {

                    e.printStackTrace();

                    calResult = "FAILED";

                    String errorStr = "calculate failed : at " + calculateDialect.toString();

                    XxlJobHelper.log(errorStr);

                    log.error(errorStr);

                } finally {

                    long endCalTimestamp = System.currentTimeMillis();

                    final String costTime = String.valueOf(endCalTimestamp - beginCalTimestamp);

                    try {

                        //最终将计算执行状态保存
                        IndexCalHandlerLogEntity calLog = IndexCalHandlerLogEntity.builder()
                                .calStartDate(calConfigMap.getStartDate())//(DateUtil.str2DateTime(calculateDialect.getStartTime(), DateUtil.DEFAULT_DATE_TIME_FORMAT))
                                .calEndDate(calConfigMap.getEndDate())
                                .handlerName(handlerName)
                                .indexCode(indexCode)
                                .intervalId(intervalId)
                                .calColumnNames(dimension.toString())
                                .createDate(new Date())
                                .calCostTime(costTime)
                                .writeNum(String.valueOf(writeNum))
                                .calResult(calResult)
                                .calType(paramsMap.get(ParamParseUtil.PARAMS_KEY_CAL_TYPE)).build();

                        indexCalConfigFeignClient.saveJobLog(JSONObject.toJSONString(calLog));

                    } catch (Exception e) {

                        e.printStackTrace();

                        String errorStr = "save calculate failed :  " + calculateDialect.toString();

                        XxlJobHelper.log(errorStr);

                        log.error(errorStr);
                    }
                }

                //依据具体的指标实体，将指标结果填入并返回
                final List<Index> sqlResultList = ClassUtil.getIndexResultListBySqlResult(clazz, sqlResultMapList, calConfigMap.getResultDate(), ownerId, intervalId, dimension.toString(), Descartes.DEFAULT_DIMENSION_VALUE);

                resList.add(sqlResultList);

                boolean saveFlag = saveIndexValue(this::beforeDoSave, calculateDialect, tenantId, sqlResultList, resultTable);

                XxlJobHelper.log("index cal result set num : [{}]", sqlResultList.size());
            }

            dialect = null;

            XxlJobHelper.log("index cal finish");

        });

        return resList;
    }

    /**
     * @desc 产生计算的起止时间,SQL方言（HIVE、MYSQL、SPARK）及 必要的参数信息
     * @author zhangheng
     * @date 2021/7/8
     * @param calType:
     * @param intervalName:
     * @param calStartTimestamp:
     * @param timeBlock:
     * @param blockSeed:
     * @param groupByCol:
     * @param originTab:
     * @return: com.gc.stcc.indexcal.common.model.CalculateDialect
     */
    private CalculateDialect byPathTimeBlockSql(ECalType calType, String intervalName, long calStartTimestamp, long timeBlock, int blockSeed, String groupByCol, String originTab, String resultTable) {

        long byBathStartTimestamp = calStartTimestamp + (blockSeed - 1) * timeBlock;

        long byBathEndTimestamp = byBathStartTimestamp + timeBlock;

        String byBathStartDate = DateUtil.date2Str(new Date(byBathStartTimestamp), DateUtil.DEFAULT_DATE_TIME_FORMAT);

        String byBathEndDate = DateUtil.date2Str(new Date(byBathEndTimestamp), DateUtil.DEFAULT_DATE_TIME_FORMAT);

        return initCalculateDialect(calType, intervalName, byBathStartDate, byBathEndDate,groupByCol, originTab, resultTable);

    }

    /**
     * @desc 时间跨度若包含大于1个的时间间隔维度，需要补算
     * @author zhangheng
     * @date 2021/7/8
     * @param calType:
     * @param calConfigMap:
     * @param dimension:
     * @param originTable:
     * @return: java.util.List<com.gc.stcc.indexcal.common.model.CalculateDialect>
     */
    private CalculateDialect byPathTimeSupplement(ECalType calType, CalConfigMap calConfigMap, StringBuffer dimension, String originTable, String resultTable) {

        final String intervalName = calConfigMap.getIntervalName();

        final DimensionTimeInterval dimensionTimeInterval = intervals.get(intervalName);
        //年 季度 月 日 等维度分别对应 事实表中的 维度字段，单次 group by 即可，即一条SQL
        //if(dimensionTimeInterval.getType() != 0) {

        //年月日，非分钟级别的 时间维度列计算时与其他维度合并group by聚合
        //如果只有时间维度，则直接拼接
        if(dimension.length() > 0) {
            dimension.append(" ,");
        }

        dimension.append(intervalName);

        String startTime = DateUtil.date2Str(calConfigMap.getStartDate(), DateUtil.DEFAULT_DATE_TIME_FORMAT);

        String endTime = DateUtil.date2Str(calConfigMap.getEndDate(), DateUtil.DEFAULT_DATE_TIME_FORMAT);

        // 分钟级计算会出现数据延迟的情况，需要从结果表获取最近一次的计算时间作为开始时间，默认结果表和事实表同源，若不同需要在具体业务指标代码中重写
        if(dimensionTimeInterval.getType() == 0) {

            final Date[] eventDateTimeSpan = fetchMaxResultEventTimeSpan(dimensionTimeInterval.getCode(), resultTable);

            startTime = DateUtil.date2Str(eventDateTimeSpan[0] == null ? calConfigMap.getStartDate() : eventDateTimeSpan[0], DateUtil.DEFAULT_DATE_TIME_FORMAT);

            endTime = DateUtil.date2Str(eventDateTimeSpan[1] == null ? calConfigMap.getEndDate() : eventDateTimeSpan[1], DateUtil.DEFAULT_DATE_TIME_FORMAT);
        }

        return initCalculateDialect(calType, intervalName, startTime, endTime, dimension.toString(), originTable, resultTable);
    }

    /**
     * @desc 根据计算方式，产生对应的SQL
     * @author zhangheng
     * @date 2021/7/8
     * @param calType:
     * @param intervalName:
     * @param startCalTime:
     * @param endCalTime:
     * @param dimensions:
     * @param oriTableName:
     * @return: com.gc.stcc.indexcal.common.model.CalculateDialect
     */
    public CalculateDialect initCalculateDialect(ECalType calType, String intervalName, String startCalTime, String endCalTime, String dimensions, String oriTableName, String resultTable) {

        CalculateDialect calculateDialect = new CalculateDialect(calType, intervalName, startCalTime, endCalTime, dimensions, oriTableName, null);

        List<String> sqlStatements = new ArrayList<>();

        switch (calType) {
            case MYSQL:
                sqlStatements.addAll(generateMysqlStatement(startCalTime, endCalTime, intervalName, dimensions, oriTableName));
                break;
            case SPARK:
                sqlStatements.addAll(generateSparkCalculate(this::doInitSparkSession, startCalTime, endCalTime, intervalName, dimensions, oriTableName));
                break;
            case HIVE:
            default:
                sqlStatements.addAll(generateHiveSqlStatement(startCalTime, endCalTime, intervalName, dimensions, oriTableName));
                break;
        }

        calculateDialect.setSqlStatements(sqlStatements);

        return calculateDialect;
    }

    /**
     * @desc
     * @author zhangheng
     * @date 2021/7/22
     * @param param:
     * @return com.xxl.job.core.biz.model.ReturnT<java.lang.Object>
     */
    public abstract ReturnT<Object> startCalculate(String param);

    /**
     * @desc 基于 HIVE、mysql、oracle 等关系型 数据的 指标计算，*如果不想定义SQL 完全依赖程序计算，可重写此方法，不重写generateXXX方法，让默认的generateXXX方法返回空集合避免报错*
     * @author zhangheng
     * @date 2021/7/8
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    protected List<Map<String, Object>> doCalculate(CalculateDialect execInfo, JdbcTemplate jdbcTemplate) throws Exception {

        List<Map<String, Object>> list = new ArrayList<>();

        execInfo.getSqlStatements().forEach(sql -> {
            list.addAll(jdbcTemplate.queryForList(sql));
        });

        return list;
    }

    /**
     * @desc 使用spark计算，默认不依赖HIVE,直接重写此方法，使用已默认初始化的 sparkSession 完成 RDD 或 SQL 处理
     * @author zhangheng
     * @date 2021/8/16
     * @param execInfo:
     * @param spark:
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    protected List<Map<String, Object>> doSparkCalculate(CalculateDialect execInfo, SparkSession spark) throws Exception {
        return null;
    }

    private List<Map<String, Object>> doCalculate(BeforeDoCalculateFunction beforeDoCalculateFunction, AfterDoCalculateFunction afterDoCalculateFunction, CalculateDialect execInfo, Object calContext) throws Exception {

        beforeDoCalculateFunction.beforeCalculate(execInfo);

        List<Map<String, Object>> resList = new ArrayList<>();

        switch (execInfo.getCalType()) {
            case SPARK:
                resList = doSparkCalculate(execInfo, spark);
                break;
            case HIVE:
            case MYSQL:
            default:
                resList = doCalculate(execInfo, (JdbcTemplate) calContext);
                break;
        }
        afterDoCalculateFunction.afterCalculate(resList);
        return resList;
    }

    /**
     * @desc 指标结果保存
     * @author zhangheng
     * @date 2021/7/8
     * @param tenantId:
     * @param sqlResultList:
     * @return boolean
     */
    public abstract boolean saveIndexValue(String tenantId, List<Index> sqlResultList, String resultTable);

    /**
     * @desc 指标结果保存
     * @author zhangheng
     * @date 2021/7/8
     * @param tenantId:
     * @param sqlResultList:
     * @return boolean
     */
    private boolean saveIndexValue(BeforeSaveIndexValueFunction<Index> beforeSaveIndexValueFunction, CalculateDialect execInfo, String tenantId, List<Index> sqlResultList, String resultTable) {
        sqlResultList = beforeSaveIndexValueFunction.beforeSaveIndexValue(tenantId, execInfo, sqlResultList);
        return saveIndexValue(tenantId, sqlResultList, resultTable);
    }

    /**
     * @desc 时间间隔维度初始化
     * @author zhangheng
     * @date 2021/7/8
     * @return java.util.Map<java.lang.String,com.gc.stcc.indexcal.common.model.DimensionTimeInterval>
     */
    protected Map<String, DimensionTimeInterval> initTimeInterval() {

        final Map<String, DimensionTimeInterval> intervals = new HashMap<>();

        String dimensionTimeIntervalTableName = "dwd_dim_time_interval";

        dialect.query("select * from " + dimensionTimeIntervalTableName, resultSet -> {

            final Integer intervalId = resultSet.getInt(resultSet.findColumn("interval_id"));

            final String name = resultSet.getString(resultSet.findColumn("name"));

            final String code = resultSet.getString(resultSet.findColumn("code"));

            final Integer activate = resultSet.getInt(resultSet.findColumn("activate"));

            final Double value = resultSet.getDouble(resultSet.findColumn("value"));

            final Integer type = resultSet.getInt(resultSet.findColumn("type"));

            intervals.put(code, new DimensionTimeInterval(intervalId, name, code, activate, value, type));

        });

        return intervals;
    }

    /**
     * @desc 默认：获取结果表中最近一次的计算时间，以此为开始时间，默认结束时间为null(时取为当前时间），要求结果表中有分钟级字段，如 min5，其他逻辑需重写
     * @author zhangheng
     * @date 2021/7/22
     * @return java.util.Date
     */
    protected Date[] fetchMaxResultEventTimeSpan(String minuteColumn, String resultTable) {

        final Date date = new Date(0);

        final String tempCol = "maxEventTimestamp";

        dialect.query("select max(unix_timestamp('"+ minuteColumn +"')) as "+ tempCol +" from " + resultTable, resultSet -> {

            final long maxTimestamp = resultSet.getLong(tempCol);

            date.setTime(maxTimestamp * 1000);

        });

        Date ret = date.getTime() == 0 ? null : date;

        return new Date[] {ret, null};
    }


    private void supplyZeroDimensionData(CalConfigMap calConfigMap, List<Map<String, Object>> needSupply, JdbcTemplate jdbcTemplate) throws Exception {

        if(needSupply == null || needSupply.size() == 0) {
            return;
        }

        final DimensionDefinitionVo.DimensionLayerInfoBean dimensionLayerInfoBean = calConfigMap.getDimensionLayerInfoBean();

        final Map<String, List<String>> dimensionColumns = new HashMap<>();

        dimensionLayerInfoBean.getDimensionGroup().forEach(dimensionGroupBean -> {

            final String dimensionTable = dimensionGroupBean.getDimensionTable();

            final List<String> orDefault = dimensionColumns.getOrDefault(dimensionTable, new ArrayList<>());

            orDefault.add(dimensionGroupBean.getDimensionCode());

            if(!dimensionColumns.containsKey(dimensionTable)) {

                dimensionColumns.put(dimensionTable, orDefault);
            }

        });

        final Set<String> allCombinSet = fetchDimensionCombination(jdbcTemplate, dimensionColumns);

        List<String> calculateColumns = new ArrayList<>();

        // 获取计算维度列名
        dimensionLayerInfoBean.getDimensionGroup().forEach(groupItem -> {

            final String dimensionColumn = groupItem.getDimensionColumn();

            calculateColumns.add(dimensionColumn);
        });

        final Set<String> currentCalculateColSet = new HashSet<>();

        needSupply.forEach(map -> {

            StringBuilder stringBuilder = new StringBuilder();

            calculateColumns.forEach(key -> {

                final String v = (String) map.get(key);

                if(stringBuilder.length() != 0) {
                    stringBuilder.append(",");
                }

                stringBuilder.append(v);
            });

            currentCalculateColSet.add(stringBuilder.toString());
        });


        for(String combination : allCombinSet) {

            if(!currentCalculateColSet.contains(combination)) {

                final String[] dimVal = combination.split(",");

                if(dimVal.length != calculateColumns.size()) {
                    throw new Exception("dimension column match error !");
                }

                final List<String> headers = calculateColumns.stream().sorted().collect(Collectors.toList());

                //每月 每天 均补
                final List<Object> collect = needSupply.parallelStream().map(map -> map.get(calConfigMap.getIntervalName())).distinct().collect(Collectors.toList());

                collect.forEach(intervalValue -> {

                    Map<String, Object> field = new HashMap<>();

                    for(int i = 0; i < headers.size(); i ++) {

                        field.put(headers.get(i), dimVal[i]);

                    }

                    field.put(calConfigMap.getIntervalName(), intervalValue);

                    needSupply.add(field);
                });

            }
        }
    }

    private Set<String> fetchDimensionCombination(JdbcTemplate jdbcTemplate, Map<String, List<String>> dimensionColumns) {

        final Set<String> tableNames = dimensionColumns.keySet();

        List<List<String>> readyDescartes = new ArrayList<>();

        tableNames.forEach(tableName -> {

            final List<String> columns = dimensionColumns.get(tableName);

            StringBuilder sqlStatement = new StringBuilder("select   *   from ").append(tableName);

            StringBuilder temp = new StringBuilder();

            for (String column : columns) {

                if(temp.length() != 0) {

                    temp.append(" ,");
                }

                temp.append(column);
            }

            sqlStatement.replace(sqlStatement.indexOf("*"), sqlStatement.indexOf("*") + 1, temp.toString());

            final List<Map<String, Object>> dimData = jdbcTemplate.queryForList(sqlStatement.toString());

            List<String> values = new ArrayList<>();

            // 同表维度字段合并
            dimData.forEach(map -> {

                final Set<String> headers = map.keySet();

                StringBuilder combinValue = new StringBuilder();

                for (String header: headers) {

                    if(combinValue.length() != 0) {

                        combinValue.append(",");
                    }
                    combinValue.append(map.get(header));
                }

                values.add(combinValue.toString());
            });

            readyDescartes.add(values);

        });

        //跨表维度字段笛卡尔积
        return doDescartes(readyDescartes);

    }

    private static Set<String > doDescartes(List<List<String>> dimvalue) {

        List<List<String>> result = new ArrayList<>();

        descartes(dimvalue, result, 0, new ArrayList<>());

        Set<String> allCombinSet = new HashSet<>();

        result.forEach(list -> {

            StringBuilder stringBuilder = new StringBuilder();

            for (String s : list) {

                if(stringBuilder.length() != 0) {

                    stringBuilder.append(",");
                }

                stringBuilder.append(s);

            }

            allCombinSet.add(stringBuilder.toString());
        });

        return allCombinSet;
    }

    private static void descartes(List<List<String>> dimvalue, List<List<String>> result, int layer, List<String> curList) {

        if (layer < dimvalue.size() - 1) {

            if (dimvalue.get(layer).size() == 0) {

                descartes(dimvalue, result, layer + 1, curList);

            } else {

                for (int i = 0; i < dimvalue.get(layer).size(); i++) {

                    List<String> list = new ArrayList<String>(curList);

                    list.add(dimvalue.get(layer).get(i));

                    descartes(dimvalue, result, layer + 1, list);

                }

            }
        } else if (layer == dimvalue.size() - 1) {

            if (dimvalue.get(layer).size() == 0) {

                result.add(curList);

            } else {

                for (int i = 0; i < dimvalue.get(layer).size(); i++) {

                    List<String> list = new ArrayList<String>(curList);

                    list.add(dimvalue.get(layer).get(i));

                    result.add(list);
                }
            }
        }
    }

    /**
     * @desc 生成HIVE风格的SQL
     * @author zhangheng
     * @date 2021/7/8
     * @param startCalTime:
     * @param endCalTime:
     * @param dimensions:
     * @param oriTableName:
     * @return: java.lang.String
     */
    protected List<String> generateHiveSqlStatement(String startCalTime, String endCalTime, String intervalName, String dimensions, String oriTableName) {
        return null;
    }

    /**
     * @desc 生成MYSQL风格的SQL
     * @author zhangheng
     * @date 2021/7/8
     * @param startCalTime:
     * @param endCalTime:
     * @param dimensions:
     * @param oriTableName:
     * @return: java.lang.String
     */
    protected List<String> generateMysqlStatement(String startCalTime, String endCalTime, String intervalName, String dimensions, String oriTableName) {
        return null;
    }

    /**
     * @desc todo
     * @author zhangheng
     * @date 2021/7/8
     * @param startCalTime:
     * @param endCalTime:
     * @param dimensions:
     * @param oriTableName:
     * @return: java.lang.String
     */
    protected List<String> generateSparkCalculate(SparkSession sparkSession, String startCalTime, String endCalTime, String intervalName, String dimensions, String oriTableName) {
        return new ArrayList<>();
    }

    private List<String> generateSparkCalculate(InitSparkSessionFunction initSparkSessionFunction, String startCalTime, String endCalTime, String intervalName, String dimensions, String oriTableName) {
        final SparkSession sparkSession = initSparkSessionFunction.initSparkSession();
        return generateSparkCalculate(sparkSession, startCalTime, endCalTime, intervalName, dimensions, oriTableName);
    }

    /**
     * @desc 引入AOP导致程序依赖复杂稳定性可读性降低，选择类似scala的函数式
     * @author zhangheng
     * @date 2021/9/1
     * @return null
     */
    @FunctionalInterface
    public interface BeforeDoCalculateFunction {
        void beforeCalculate(CalculateDialect execInfo);
    }

    @FunctionalInterface
    public interface AfterDoCalculateFunction {
        void afterCalculate(List<Map<String, Object>> resList);
    }

    @FunctionalInterface
    public interface BeforeSaveIndexValueFunction<Index> {
        List<Index> beforeSaveIndexValue(String tenantId, CalculateDialect execInfo, List<Index> list);
    }

    @FunctionalInterface
    public interface InitSparkSessionFunction {
        SparkSession initSparkSession();
    }

    public void beforeDoCalculate(CalculateDialect execInfo) {

    }

    public void afterDoCalculate(List<Map<String, Object>> list) {

    }

    public List<Index> beforeDoSave(String tenantId, CalculateDialect execInfo, List<Index> list) {
        return list;
    }

    public SparkSession doInitSparkSession() {

        //TODO spark执行参数从xxl任务参数中获取

        final SparkConf sparkConf = new SparkConf().setMaster("yarn").setAppName("index_taxi_online_num_cal")
                .set("deploy-mode", "cluster")
                .set("spark.yarn.archive", "hdfs://gcits-had02-196:8020/tmp/spark-task-ref/lib/jars.zip")
                .set("spark.yarn.dist.jars", "hdfs://gcits-had02-196:8020/tmp/spark-task-ref/jobs");

        final SparkSession spark = SparkSession.builder().config(sparkConf).getOrCreate();

        this.spark = spark;

        return spark;
    }

}
