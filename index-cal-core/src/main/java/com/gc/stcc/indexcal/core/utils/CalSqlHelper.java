package com.gc.stcc.indexcal.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * @author hsd
 * @date 2020/8/11 11:34
 */
@Slf4j
public class CalSqlHelper {


    public static String getCalSql(String startCalTime, String endCalTime, String compareDateName, String dimensions, String oriTableName) {
        if(StringUtils.isEmpty(dimensions)) {
            return selectCountNum(startCalTime, endCalTime, compareDateName, oriTableName);
        } else {
            return selectCountNumGroupByDimension(startCalTime, endCalTime, compareDateName, dimensions, oriTableName);
        }
    }

    private static String selectCountNumGroupByDimension(String startCalTime, String endCalTime, String compareDateName, String dimensions, String oriTableName) {
        StringBuilder sql = new StringBuilder();
        sql.append(" select count(*) as NUM, ");
        sql.append(" " + dimensions);
        sql.append(" from " + oriTableName);
        sql.append(" where 1=1 ");
        sql.append(" and " + compareDateName + " between '" + startCalTime + "' and '" + endCalTime + "'");
        sql.append(" group by ");
        sql.append(" " + dimensions);
        log.info("执行的sql语句为: "+ sql.toString());
        return sql.toString();
    }

    private static String selectCountNum(String startCalTime, String endCalTime, String compareDateName, String oriTableName) {
        StringBuilder sql = new StringBuilder();
        sql.append(" select count(*) as NUM ");
        sql.append(" from "+oriTableName);
        sql.append(" where 1=1 ");
        sql.append(" and "+ compareDateName +" between '"+startCalTime+"' and '"+endCalTime+"'");
        log.info("执行的sql语句为: "+sql.toString());
        return sql.toString();
    }

}
