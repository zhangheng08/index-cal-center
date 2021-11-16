package com.gc.stcc.indexcal.core.utils;

import com.gc.stcc.indexcal.common.model.DbConnParam;
import com.gc.stcc.indexcal.core.ds2.config.DynamicDataSource;
import com.gc.stcc.indexcal.core.ds2.context.SpringContextHolder;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

/**
 * @Copyright (C) 2021, 华录高诚
 * @FileName: DataSourceUtil
 * @Author: ZhangHeng
 * @Date: 2021/8/2 9:03
 * @Description:
 * @Version:
 */
public class DataSourceUtil {

    /**
     * 创建新的数据源
     * @param dbInfo
     * @return
     */
    public static DataSource makeNewDataSource(DbConnParam dbInfo){

        switch (dbInfo.getDbType()) {
            case DataSourceConstants.DS_DB_HIVE:

                return null;
            case DataSourceConstants.DS_DB_MYSQL:
            default:
                String url = "jdbc:mysql://"+dbInfo.getIp() + ":"+dbInfo.getPort()+"/"+dbInfo.getDbName()+"?useSSL=false&serverTimezone=GMT%2B8&characterEncoding=UTF-8";
                String driveClassName = StringUtils.isEmpty(dbInfo.getDriveClassName())? "com.mysql.cj.jdbc.Driver" : dbInfo.getDriveClassName();
                return DataSourceBuilder.create().url(url)
                        .driverClassName(driveClassName)
                        .username(dbInfo.getUsername())
                        .password(dbInfo.getPassword())
                        .build();
        }
    }

    /**
     * 添加数据源到动态源中
     * @param key
     * @param dataSource
     */
    public static void addDataSourceToDynamic(String key, DataSource dataSource){
        DynamicDataSource dynamicDataSource = SpringContextHolder.getContext().getBean(DynamicDataSource.class);
        dynamicDataSource.addDataSource(key,dataSource);
    }

    /**
     * 根据数据库连接信息添加数据源到动态源中
     * @param key
     * @param dbInfo
     */
    public static void addDataSourceToDynamic(String key, DbConnParam dbInfo){
        DataSource dataSource = makeNewDataSource(dbInfo);
        addDataSourceToDynamic(key,dataSource);
    }

    public static JdbcTemplate initParamDynamicDbTemplate(String key, DbConnParam dbInfo) {
        DataSource dataSource = makeNewDataSource(dbInfo);
        addDataSourceToDynamic(key,dataSource);
        return new JdbcTemplate(dataSource);
    }
}
