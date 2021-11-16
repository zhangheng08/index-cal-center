package com.gc.stcc.indexcal.sz.distcongest.job;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gc.stcc.indexcal.common.enums.ECalType;
import com.gc.stcc.indexcal.common.model.CalculateDialect;
import com.gc.stcc.indexcal.common.model.DimensionTimeInterval;
import com.gc.stcc.indexcal.core.job.CommonJob;
import com.gc.stcc.indexcal.core.utils.DateUtil;
import com.gc.stcc.indexcal.core.utils.StringUtil;
import com.gc.stcc.indexcal.sz.distcongest.entiy.model.DmDistCongestionMinu;
import com.gc.stcc.indexcal.sz.distcongest.entiy.model.OdsInternetDistCongestY;
import com.gc.stcc.indexcal.sz.distcongest.service.DmDistCongestionMinuService;
import com.gc.stcc.indexcal.sz.distcongest.service.OdsInternetDistCongestYservice;
import com.gc.stcc.indexcal.sz.distcongest.service.OdsYthjcSysDistrictYService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @Copyright (C) 2021, 华录高诚
 * @FileName: DistCongestionMinuJob
 * @Author: ZhangHeng
 * @Date: 2021/7/21 13:55
 * @Description:
 * @Version:
 */
@Slf4j
@Component
public class DistCongestionMinuJob extends CommonJob<DmDistCongestionMinu> {

    @Autowired
    private JdbcTemplate mysqlTemplate;

    @Autowired
    private DmDistCongestionMinuService dmDistCongestionMinuService;

    @Autowired
    private OdsInternetDistCongestYservice odsInternetDistCongestYservice;

    @Autowired
    private OdsYthjcSysDistrictYService odsYthjcSysDistrictYService;

    @XxlJob("index_sz_dist_congestion_minu")
    @Override
    public ReturnT<Object> startCalculate(String params) {

        ReturnT<Object> rt = new ReturnT<>();

        if (StringUtil.isEmpty(params)) {
            params = XxlJobHelper.getJobParam();
        }

        final String handlerName = getHandlerName(Thread.currentThread().getStackTrace()[1].getMethodName());

        XxlJobHelper.log("cal start: params" + params);
        log.info("cal start: params" + params);

        try {

            final List<List<DmDistCongestionMinu>> calculate = calculate(DmDistCongestionMinu.class, handlerName, params, ECalType.MYSQL, mysqlTemplate);

            rt.setContent(calculate);
            rt.setMsg(ReturnT.SUCCESS.getMsg());

        } catch (Exception e) {

            e.printStackTrace();
            rt.setMsg(ReturnT.FAIL.getMsg());

        }

        XxlJobHelper.log("cal end: params" + params);
        log.info("cal end: params" + params);

        return rt;
    }

    @Override
    protected List<String> generateMysqlStatement(String startCalTime, String endCalTime, String intervalCode, String dimensions, String oriTableName) {


        List<String> list = new ArrayList<>();

        try {

            final List<Object> sqlStatements = doProcess((innerStartCalTime, innerEndCalTime) -> {

                /*
                 * 1.当 拥堵结束时间>统计结束时间 && 拥堵开始时间>统计开始时间  : 该时段拥堵时间=统计结束时间-拥堵开始时间
                 * 2.当 拥堵结束时间>统计结束时间 && 拥堵开始时间<统计开始时间  : 该时段拥堵时间=5分钟
                 * 3.当 拥堵结束时间<统计结束时间 && 当拥堵开始时间<统计开始时间: 该时段拥堵时间=拥堵结束时间-统计开始时间
                 * 4.当 拥堵结束时间<统计结束时间 && 当拥堵开始时间>统计开始时间: 该时刻拥堵时间=congest_keeptime（拥堵开始时间-拥堵结束时间）
                 */
                final String sql = "" +
                        "select sum(duration) as congestDuration, " + dimensions + " from \n" +
                        "(\n" +
                        "\tselect tab2.*, substring('" + innerEndCalTime + "', 1, 4) as year, substring('" + innerEndCalTime + "', 1, 7) as month, substring('" + innerEndCalTime + "', 1, 10) as day, concat('" + innerEndCalTime + "') as min5\n" +
                        "\tfrom (\n" +
                        "\t\tselect tab1.*, (unix_timestamp('" + innerEndCalTime + "') - unix_timestamp(tab1.START_TIME)) as duration \n" +
                        "\t\tfrom \n" +
                        "\t\t(\n" +
                        "\t\tselect congest_id, start_time, end_time, congest_keeptime, dist_id from `ODS_YTHJC_INTERNET_ROAD_CONGEST_Y` where CONGEST_STATUS in (1, 5) and START_TIME <= '" + innerEndCalTime + "' and END_TIME >= '" + innerStartCalTime + "'\n" +
                        "\t\t) as tab1\n" +
                        "\t\twhere tab1.END_TIME > '" + innerEndCalTime + "' and tab1.START_TIME > '" + innerStartCalTime + "'\n" +
                        "\t\tunion all\n" +
                        "\t\tselect tab1.*, (5 * 60) as duration \n" +
                        "\t\tfrom \n" +
                        "\t\t(\n" +
                        "\t\tselect congest_id, start_time, end_time, congest_keeptime, dist_id from `ODS_YTHJC_INTERNET_ROAD_CONGEST_Y` where CONGEST_STATUS in (1, 5) and START_TIME <= '" + innerEndCalTime + "' and END_TIME >= '" + innerStartCalTime + "'\n" +
                        "\t\t) as tab1\n" +
                        "\t\twhere tab1.END_TIME > '" + innerEndCalTime + "' and tab1.START_TIME < '" + innerStartCalTime + "'\n" +
                        "\t\tunion all\n" +
                        "\t\tselect tab1.*, (unix_timestamp(tab1.END_TIME) - unix_timestamp('" + innerStartCalTime + "')) as duration \n" +
                        "\t\tfrom \n" +
                        "\t\t(\n" +
                        "\t\tselect congest_id, start_time, end_time, congest_keeptime, dist_id from `ODS_YTHJC_INTERNET_ROAD_CONGEST_Y` where CONGEST_STATUS in (1, 5) and START_TIME <= '" + innerEndCalTime + "' and END_TIME >= '" + innerStartCalTime + "'\n" +
                        "\t\t) as tab1\n" +
                        "\t\twhere tab1.END_TIME < '" + innerEndCalTime + "' and tab1.START_TIME < '" + innerStartCalTime + "'\n" +
                        "\t\tunion all\n" +
                        "\t\tselect tab1.*, (unix_timestamp(tab1.END_TIME) - unix_timestamp(tab1.START_TIME)) as duration \n" +
                        "\t\tfrom \n" +
                        "\t\t(\n" +
                        "\t\tselect congest_id, start_time, end_time, congest_keeptime, dist_id from `ODS_YTHJC_INTERNET_ROAD_CONGEST_Y` where CONGEST_STATUS in (1, 5) and START_TIME <= '" + innerEndCalTime + "' and END_TIME >= '" + innerStartCalTime + "'\n" +
                        "\t\t) as tab1\n" +
                        "\t\twhere tab1.END_TIME < '" + innerEndCalTime + "' and tab1.START_TIME > '" + innerStartCalTime + "'\n" +
                        "\t) as tab2\n" +
                        ") as " + oriTableName + " group by " + dimensions;

                return sql;

            }, startCalTime, endCalTime, intervalCode);

            sqlStatements.forEach(sql -> {
                list.add(sql.toString());
            });

        } catch (Exception e) {

            e.printStackTrace();

        }

        return list;

    }

    /**
     * @desc
     * xxl任务每次驱动的计算：
     * 1.首次计算 统计开始时间由参数指定，统计结束时间应取自ODS_YTHJC_INTERNET_DIST_CONGEST_Y的create_time字段，时间范围内所有统计时间段一次性补算
     * 2.非首次，统计开始时间取自结果表中create_time字段，统计结束时间同上
     * 3.有任务中断，取值同上2，时间范围内所有统计时间段一次性补算
     * @author zhangheng
     * @date 2021/7/23
     * @param minuteColumn:
     * @param resultTable:
     * @return: java.util.Date[]
     */
    @Override
    public Date[] fetchMaxResultEventTimeSpan(String minuteColumn, String resultTable) {

        final Date dateResField = new Date(0);

        final String tempCol = "maxEventTimestamp";

        mysqlTemplate.query("select max(unix_timestamp(create_time)) as "+ tempCol +" from " + resultTable, resultSet -> {

            final long maxTimestamp = resultSet.getLong(tempCol);

            dateResField.setTime(maxTimestamp * 1000);

        });


        final Date dateDistY= new Date(0);

        mysqlTemplate.query("select max(unix_timestamp(create_time)) as "+ tempCol +" from ODS_YTHJC_INTERNET_DIST_CONGEST_Y", resultSet -> {

            final long maxTimestamp = resultSet.getLong(tempCol);

            dateDistY.setTime(maxTimestamp * 1000);

        });

        Date dateBegin = null;

        if(dateResField.getTime() > 0) {

            dateBegin = (dateDistY.getTime() - dateResField.getTime()) > 0 ? dateResField : null;
        }

        return new Date[] {dateBegin, dateDistY};
    }

    /**
     * @desc 调用父类方法 获取上一步的聚合结果值，从 ODS_YTHJC_INTERNET_DIST_CONGEST_Y 表读取已有的指标，补至结果集中
     * @author zhangheng
     * @date 2021/7/23
     * @param execInfo:
     * @param jdbcTemplate:
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @Override
    public void afterDoCalculate(List<Map<String, Object>> maps) {

        for (Map<String, Object> map : maps) {

            map.put("create_time", map.get("min5"));
        }
    }

    @Override
    public List<DmDistCongestionMinu> beforeDoSave(String tenantId, CalculateDialect execInfo, List<DmDistCongestionMinu> sqlResultList) {

        try {

            final List<DmDistCongestionMinu> _sqlResultList = sqlResultList;

            final List<Object> objs = doProcess((innerStartTime, innerEndTime) -> {

                LambdaQueryWrapper<OdsInternetDistCongestY> conditionWraper = new QueryWrapper<OdsInternetDistCongestY>().lambda();

                try {
                    conditionWraper
                            .eq(OdsInternetDistCongestY::getRoadType, "0")
                            .gt(OdsInternetDistCongestY::getCreateTime, DateUtil.str2Date(innerStartTime + "", DateUtil.DEFAULT_DATE_TIME_FORMAT))
                            .le(OdsInternetDistCongestY::getCreateTime, DateUtil.str2Date(innerEndTime + "", DateUtil.DEFAULT_DATE_TIME_FORMAT));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                List<OdsInternetDistCongestY> originAllDataList = odsInternetDistCongestYservice.list(conditionWraper);

                List<DmDistCongestionMinu> resultList = new ArrayList<>();

                //拼接结果
                for (OdsInternetDistCongestY origin : originAllDataList) {

                    DmDistCongestionMinu result = new DmDistCongestionMinu();

                    result.setDistId(origin.getDistId());

                    result.setDistName(odsYthjcSysDistrictYService.getDistNameById(origin.getDistId()));

                    result.setCongestIndex(origin.getCongestIndex());

                    result.setSpeedAvg(origin.getRoadSpeed());

                    result.setCongestMileage(origin.getCongestLength1());

                    result.setTimeIntervalId(Integer.parseInt(origin.getInternalId()));

                    result.setUpdateTime(new Date());

                    result.setCreateTime(origin.getCreateTime());

                    //获得拥堵指数同比
                    //平均速度同比,对比去年
                    LambdaQueryWrapper<DmDistCongestionMinu> step1 = new QueryWrapper<DmDistCongestionMinu>().lambda();
                    step1.select(DmDistCongestionMinu::getId
                            , DmDistCongestionMinu::getCongestIndex
                            , DmDistCongestionMinu::getSpeedAvg
                            , DmDistCongestionMinu::getCreateTime)
                            .eq(DmDistCongestionMinu::getCreateTime, DateUtil.dateOffsetBy(origin.getCreateTime(), Calendar.MONTH, -12))
                            .eq(DmDistCongestionMinu::getDistId, origin.getDistId());

                    List<DmDistCongestionMinu> yearList = dmDistCongestionMinuService.list(step1);

                    if (yearList.size() > 0) {

                        DmDistCongestionMinu yearEntity = yearList.get(0);
                        if (yearEntity.getCongestIndex() != null && yearEntity.getCongestIndex() != 0
                                && result.getCongestIndex() != null) {
                            result.setCongestIndexCsy(
                                    result.getCongestIndex() / yearEntity.getCongestIndex() - 1);
                        }

                        if (yearEntity.getSpeedAvg() != null && yearEntity.getSpeedAvg() != 0
                                && result.getSpeedAvg() != null) {
                            result.setSpeedAvgCsy(
                                    result.getSpeedAvg() / yearEntity.getSpeedAvg() - 1);
                        }

                    }

                    //获得拥堵始数环比
                    //平均速度环比,对比上一个月或上一周
                    LambdaQueryWrapper<DmDistCongestionMinu> step2 = new QueryWrapper<DmDistCongestionMinu>().lambda();

                    step2.select(DmDistCongestionMinu::getId
                            , DmDistCongestionMinu::getCongestIndex
                            , DmDistCongestionMinu::getSpeedAvg
                            , DmDistCongestionMinu::getCreateTime)
                            .eq(DmDistCongestionMinu::getCreateTime, DateUtil.dateOffsetBy(origin.getCreateTime(), Calendar.WEEK_OF_MONTH, -1))
                            .eq(DmDistCongestionMinu::getDistId, origin.getDistId());

                    List<DmDistCongestionMinu> weekList = dmDistCongestionMinuService.list(step2);

                    if (weekList.size() > 0) {

                        DmDistCongestionMinu weekEntity = weekList.get(0);
                        if (weekEntity.getCongestIndex() != null && weekEntity.getCongestIndex() != 0
                                && result.getCongestIndex() != null) {
                            result.setCongestIndexCsm(
                                    result.getCongestIndex() / weekEntity.getCongestIndex() - 1);
                        }

                        if (weekEntity.getSpeedAvg() != null && weekEntity.getSpeedAvg() != 0
                                && result.getSpeedAvg() != null) {
                            result.setSpeedAvgCsm(
                                    result.getSpeedAvg() / weekEntity.getSpeedAvg() - 1);
                        }
                    }

                    final List<DmDistCongestionMinu> one = _sqlResultList.stream().filter(item -> item.getDistId().equals(result.getDistId()) && item.getCreateTime().equals(result.getCreateTime())).collect(Collectors.toList());

                    if(one.size() != 0) {
                        result.setCongestDuration(one.get(0).getCongestDuration() / 60);
                    }

                    resultList.add(result);
                }

                return resultList;

            }, execInfo.getStartTime(), execInfo.getEndTime(), execInfo.getIntervalName());

            sqlResultList.clear();

            objs.forEach(list -> {
                sqlResultList.addAll((List<DmDistCongestionMinu>)list);
            });

        } catch (Exception e) {

            e.printStackTrace();
        }

        return sqlResultList;
    }

    /**
     * @desc 指标结果保存，具体的指标结果表 和 计算任务的业务相关，保存操做需由
     * @author zhangheng
     * @date 2021/7/23
     * @param tenantId:
     * @param sqlResultList:
     * @return boolean
     */
    @DS("cal")
    @Override
    public boolean saveIndexValue(String tenantId, List<DmDistCongestionMinu> sqlResultList, String resultTable) {

        return dmDistCongestionMinuService.saveBatch(sqlResultList);
    }

    public List<Object> doProcess(ProcessWithTimeSpan processWithTimeSpan, String startCalTime, String endCalTime, String ... args) throws Exception {

        List<Object> list = new ArrayList<>();

        final Date startCalDate = DateUtil.str2Date(startCalTime, DateUtil.DEFAULT_DATE_TIME_FORMAT);

        final long startCalTimestamp = startCalDate.getTime();

        final long endCalTimestamp = DateUtil.str2Date(endCalTime, DateUtil.DEFAULT_DATE_TIME_FORMAT).getTime();

        final DimensionTimeInterval dimensionTimeInterval = intervals.get(args[0]);

        final double diff =  (endCalTimestamp - startCalTimestamp) / (60000 * dimensionTimeInterval.getValue());

        final double span = Math.ceil(diff);

        for (int i = 0; i < span; i ++) {

            final Date innerStartDate = DateUtil.addMinute(startCalDate, i * dimensionTimeInterval.getValue().intValue());

            final String innerStartCalTime = DateUtil.date2Str(innerStartDate, DateUtil.DEFAULT_DATE_TIME_FORMAT);

            final String innerEndCalTime = DateUtil.date2Str(DateUtil.addMinute(innerStartDate, dimensionTimeInterval.getValue().intValue()), DateUtil.DEFAULT_DATE_TIME_FORMAT);

            final Object obj = processWithTimeSpan.process(innerStartCalTime, innerEndCalTime);

            list.add(obj);

        }

        return list;
    }

    @FunctionalInterface
    public interface ProcessWithTimeSpan {

        Object process(final String startTime, final String endTime);

    }


}
