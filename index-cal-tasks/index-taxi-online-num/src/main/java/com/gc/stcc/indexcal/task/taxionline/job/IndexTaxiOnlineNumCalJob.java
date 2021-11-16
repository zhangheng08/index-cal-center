package com.gc.stcc.indexcal.task.taxionline.job;

import com.gc.stcc.indexcal.common.enums.ECalType;
import com.gc.stcc.indexcal.core.job.CommonJob;
import com.gc.stcc.indexcal.core.utils.StringUtil;
import com.gc.stcc.indexcal.task.taxionline.entity.IndexTaxiWorkNumEntity;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @Copyright (C) 2021, 华录高诚
 * @FileName: IndexTaxiOnlineNumCalJob
 * @Author: ZhangHeng
 * @Date: 2021/7/5 14:22
 * @Description:
 * @Version:
 */
@Slf4j
@Component
public class IndexTaxiOnlineNumCalJob extends CommonJob <IndexTaxiWorkNumEntity> {

    @Autowired
    @Qualifier("hiveDruidTemplate")
    private JdbcTemplate hiveSqlTemplate;
    @Autowired
    @Qualifier("mysqlDruidTemplate")
    private JdbcTemplate mysqlTemplate;

    /**
     * @param params: task exec params
     * @desc
     * @author zhangheng
     * @date 2021/7/5
     * @return: com.xxl.job.core.biz.model.ReturnT<java.lang.String>
     */
    @XxlJob("index_taxi_online_num_cal")
    @Override
    public ReturnT<Object> startCalculate(String params) {

        ReturnT<Object>  rt = new ReturnT<>();

        if (StringUtil.isEmpty(params)) {

            params = XxlJobHelper.getJobParam();
        }

        final String handlerName = getHandlerName(Thread.currentThread().getStackTrace()[1].getMethodName());

        XxlJobHelper.log("cal start: params" + params);
        log.info("cal start: params" + params);

        try {

            final List<List<IndexTaxiWorkNumEntity>> calculateResult = calculate(IndexTaxiWorkNumEntity.class, handlerName, params, ECalType.HIVE, hiveSqlTemplate);

            rt.setContent(calculateResult);
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
    public List<String> generateHiveSqlStatement(String startCalTime, String endCalTime, String intervalCode, String dimensions, String oriTableName) {
        List<String> sqlStatement = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append(" select count(*) as num, ")
        .append(" " + dimensions)
        .append(" from " + oriTableName)
        .append(" where 1 = 1 ")
        .append(" and " + "up_time" + " between '" + startCalTime + "' and '" + endCalTime + "'")
        .append(" group by ")
        .append(" " + dimensions);
        log.info("sql: "+ sql.toString());
        sqlStatement.add(sql.toString());
        return sqlStatement;
    }

    @Override
    public Date[] fetchMaxResultEventTimeSpan(String minuteColumn, String resultTable) {

        final Date date = new Date(0);

        final String tempCol = "maxEventTimestamp";

        mysqlTemplate.query("select max(unix_timestamp("+ minuteColumn +")) as "+ tempCol +" from " + resultTable, resultSet -> {

            final long maxTimestamp = resultSet.getLong(tempCol);

            date.setTime(maxTimestamp * 1000);

        });

        Date ret = date.getTime() == 0 ? null : date;

        return new Date[] {ret, null};
    }

    @Override
    public boolean saveIndexValue(String tenantId, List<IndexTaxiWorkNumEntity> sqlResultList, String resultTable) {

        final List<IndexTaxiWorkNumEntity> saveList = sqlResultList;

        try {

            saveList.forEach(indexTaxiWorkNumEntity -> {
                mysqlTemplate.update("INSERT INTO `index_cal_center`.`index_cal_taxi_online_num` (`num`,`year`,`quarter`,`month`,`day`,`min5`,`ext1`,`ext2`,`tenant_id`) values (?, ?, ?, ?, ?, ?, ?, ?, ?) ",
                        new Object[]{indexTaxiWorkNumEntity.getNum() == null ? 0 : indexTaxiWorkNumEntity.getNum(), indexTaxiWorkNumEntity.getYear(), indexTaxiWorkNumEntity.getQuarter(), indexTaxiWorkNumEntity.getMonth(), indexTaxiWorkNumEntity.getDay(), indexTaxiWorkNumEntity.getMin5(), indexTaxiWorkNumEntity.getExt1(), indexTaxiWorkNumEntity.getExt2(), tenantId},
                        new int[] {Types.BIGINT, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR});
            });

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

}
