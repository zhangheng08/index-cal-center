package com.gc.stcc.indexcal.core.utils;

import com.gc.stcc.indexcal.core.exception.ParamParseException;
import com.xxl.job.core.context.XxlJobHelper;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hsd
 * @date 2020/8/6 11:31
 */
@Slf4j
public class ParamParseUtil {

    public final static String PARAMS_KEY_TENANT_ID = "tenant_id";
    public final static String PARAMS_KEY_INDEX_CODE = "index_code";
    public final static String PARAMS_KEY_INTERVAL_ID = "interval_id";
    public final static String PARAMS_KEY_START_CAL_TIME = "start_cal_time";
    public final static String PARAMS_KEY_FIRST_CAL_TIME = "first_cal_time";
    public final static String PARAMS_KEY_RECAL_START_TIME = "recal_start_time";
    public final static String PARAMS_KEY_RECAL_END_TIME = "recal_end_time";
    public final static String PARAMS_KEY_DIMENSION_DEF_ID = "dimension_def_id";
    public final static String PARAMS_KEY_INTERVAL_CAL_DIMENSIONS = "interval_dim";
    // 指标对应的ownerId
    public final static String PARAMS_KEY_OWNER_ID = "owner_id";
    // 计算类型: 系统延时计算、指定的单次计算 没有值默认是 系统计算, 如果是手动执行计算或者重新计算 则必须有start_cal_time参数
    public final static String PARAMS_KEY_CAL_TYPE = "cal_type";
    // 系统计算 会读取计算日志表cal_result为SUCCESS的数据 依照最新值进行计算
    public final static String CAL_TYPE_SYSTEM = "SYSTEM";
    // 重新计算 会根据recal_start_time 和 recal_end_time 将计算日志在时间范围内的 SUCCESS数据 改为RECAL
    public final static String CAL_TYPE_RECAL = "RECAL";
    // 手动一次计算 会根据start_cal_time 计算一次 始终计算该值
    public final static String CAL_TYPE_ONCE = "ONCE";

    /**
     *
     * -index_code {指标定义中的指标编码} 可为空，为空则需在计算时进行补充该值
     * -interval_id {时间粒度值} 不可为空
     * -start_cal_Time {yyyyMMddTHHmmss} 可为空，为空则取计算记录表当前时间粒度的下次计算日期
     * -origin_table_name {元数据的表名} 可为空，为空则取配置表中的元数据表
     *
     * @example -index_code taxi_density -interval_id 2 -start_cal_time 20200806T000000 -origin_table_name taxi_record
     * @param params
     * @return
     */
    public static Map<String, String> paramParse(String params) throws Exception {
        XxlJobHelper.log("params parse begin");
        Map<String, String> paramsMap = new HashMap<>();
        if(StringUtil.isEmpty(params)) {
            String errorStr = "index cal params error : params is null ? : " + params;
            XxlJobHelper.log(errorStr);
            log.error(errorStr);
            throw new ParamParseException(errorStr);
        }

        try {
            for (String str: params.split("-")) {
                if(StringUtil.isNotEmpty(str)) {
                    paramsMap.put(str.split(" ")[0].trim(), str.split(" ")[1].trim());
                }
            }
        } catch (Exception e) {
            String errorStr = "index cal params error : params" + params;
            XxlJobHelper.log(errorStr);
            log.error(errorStr);
            throw new ParamParseException(errorStr+e);
        }

        if(!paramsMap.containsKey(PARAMS_KEY_INTERVAL_CAL_DIMENSIONS)){
            String errorStr = "absence param : interval_dim";
            XxlJobHelper.log(errorStr);
            log.error(errorStr);
            throw new ParamParseException(errorStr);
        }

        if(!paramsMap.containsKey(PARAMS_KEY_TENANT_ID)){
            String errorStr = "absence param : tenant_id";
            XxlJobHelper.log(errorStr);
            log.error(errorStr);
            throw new ParamParseException(errorStr);
        }

        if(!paramsMap.containsKey(PARAMS_KEY_INDEX_CODE)){
            String errorStr = "absence param : index_code";
            XxlJobHelper.log(errorStr);
            log.error(errorStr);
            throw new ParamParseException(errorStr);
        }

        if(!paramsMap.containsKey(PARAMS_KEY_CAL_TYPE)){
            // 如果没有计算类型 则赋值为 系统计算
            paramsMap.put(PARAMS_KEY_CAL_TYPE, CAL_TYPE_SYSTEM);
        }

        if(paramsMap.containsKey(PARAMS_KEY_CAL_TYPE)
                && !paramsMap.get(PARAMS_KEY_CAL_TYPE).equalsIgnoreCase(CAL_TYPE_SYSTEM)
                && !paramsMap.containsKey(PARAMS_KEY_START_CAL_TIME)){
            // 如果计算类型 不是系统计算 则需要传递start_cal_time 参数抛出异常
            String errorStr = "if not a SYSTEM type, you must give the params contain [start_cal_time]";
            XxlJobHelper.log(errorStr);
            log.error(errorStr);
            throw new ParamParseException(errorStr);
        }

        if(paramsMap.containsKey(PARAMS_KEY_CAL_TYPE)
                && !paramsMap.get(PARAMS_KEY_CAL_TYPE).equalsIgnoreCase(CAL_TYPE_SYSTEM)
                && paramsMap.containsKey(PARAMS_KEY_START_CAL_TIME)
                && !isValidDate(paramsMap.get(PARAMS_KEY_START_CAL_TIME))){
            String errorStr = "start_cal_time format error, the right format is : yyyy_MM_ddTHH:mm:ss";
            XxlJobHelper.log(errorStr);
            log.error(errorStr);
            throw new ParamParseException(errorStr);
        }

        if(paramsMap.containsKey(PARAMS_KEY_CAL_TYPE)
                && paramsMap.get(PARAMS_KEY_CAL_TYPE).equalsIgnoreCase(CAL_TYPE_RECAL)) {
            if (!paramsMap.containsKey(PARAMS_KEY_RECAL_START_TIME)) {
                String errorStr = "use a RECAL type you must give the params contain: [recal_start_time] and [recal_end_time]";
                XxlJobHelper.log(errorStr);
                log.error(errorStr);
                throw new ParamParseException(errorStr);
            } else if(!isValidDate(paramsMap.get(PARAMS_KEY_RECAL_START_TIME))) {
                String errorStr = "recal_start_time format error ,right format is yyyy_MM_ddTHH:mm:ss";
                XxlJobHelper.log(errorStr);
                log.error(errorStr);
                throw new ParamParseException(errorStr);
            }
        }

        if(paramsMap.containsKey(PARAMS_KEY_CAL_TYPE)
                && paramsMap.get(PARAMS_KEY_CAL_TYPE).equalsIgnoreCase(CAL_TYPE_RECAL)
                && paramsMap.containsKey(PARAMS_KEY_RECAL_END_TIME)){
            if(!isValidDate(paramsMap.get(PARAMS_KEY_RECAL_END_TIME))){
                String errorStr = "recal_end_time format error ,right format is yyyy_MM_ddTHH:mm:ss";
                XxlJobHelper.log(errorStr);
                log.error(errorStr);
                throw new ParamParseException(errorStr);
            }
        }

        if(paramsMap.containsKey(PARAMS_KEY_INTERVAL_CAL_DIMENSIONS)) {
            final String[] intervalCalDims = paramsMap.get(ParamParseUtil.PARAMS_KEY_INTERVAL_CAL_DIMENSIONS).split("-");
            final long noneDimCount = Arrays.stream(intervalCalDims).filter(
                    intervalName ->
                            "min5".equals(intervalName)
                                    || "min15".equals(intervalName)
                                    || "min30".equals(intervalName)
                                    || "min60".equals(intervalName)
            ).count();

            final long dimCount = Arrays.stream(intervalCalDims).filter(
                    intervalName ->
                            "year".equals(intervalName)
                                    || "quarter".equals(intervalName)
                                    || "month".equals(intervalName)
                                    || "week".equals(intervalName)
                                    || "day".equals(intervalName)
            ).count();

            if(noneDimCount > 1 || (noneDimCount != 0 && dimCount != 0)) {
                String errorStr = "none dimension interval must be calculate in a single job.";
                XxlJobHelper.log(errorStr);
                log.error(errorStr);
                throw new ParamParseException(errorStr);
            }
        }

        XxlJobHelper.log("params parse finish");
        return paramsMap;
    }

    public static boolean isValidDate(String str) {

        boolean convertSuccess = true;
        try {
            str = str.replace("_", "-").replace("T", " ");
            SimpleDateFormat format = new SimpleDateFormat(DateUtil.DEFAULT_DATE_TIME_FORMAT);
            format.parse(str);
        } catch (ParseException e) {
            convertSuccess = false;
        }
        return convertSuccess;
    }

    public static Date getDate(Map<String, String> paramsMap, String key){
        Date date = new Date();
        if(paramsMap.containsKey(key)){
            String str = paramsMap.get(key);
            SimpleDateFormat format = new SimpleDateFormat(DateUtil.DEFAULT_DATE_TIME_FORMAT);
            try {
                date = format.parse(str);
            } catch (ParseException e) {
                return date;
            }
            return date;
        }
        return date;
    }
}
