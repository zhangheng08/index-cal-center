package com.gc.stcc.indexcal.core.ds2.config;

import com.gc.stcc.indexcal.core.utils.DataSourceConstants;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Copyright (C) 2021, 华录高诚
 * @FileName: DynamicDataSourceConfig
 * @Author: ZhangHeng
 * @Date: 2021/8/2 9:13
 * @Description:
 * @Version:
 */
@Configuration
@PropertySource("classpath:config/ds.properties")
public class DynamicDataSourceConfig {

    @Bean(DataSourceConstants.DS_KEY_MASTER)
    @ConfigurationProperties(prefix = "spring.datasource.master")
    @Qualifier("masterDataSource")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(DataSourceConstants.DS_KEY_DWD)
    @ConfigurationProperties(prefix = "spring.datasource.dwd")
    @Qualifier("hiveDataSource")
    public DataSource hiveDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    public DataSource dynamicDataSource() {
        Map<Object, Object> dataSourceMap = new HashMap<>(2);
        dataSourceMap.put(DataSourceConstants.DS_KEY_MASTER, masterDataSource());
        dataSourceMap.put(DataSourceConstants.DS_KEY_DWD, hiveDataSource());
        return new DynamicDataSource(masterDataSource(), dataSourceMap);
    }

    @Bean(name = "hiveTemplate")
    public JdbcTemplate hiveTemplate(@Qualifier("hiveDataSource") DataSource hiveDataSource) {
        return new JdbcTemplate(hiveDataSource);
    }

    @Bean(name = "masterTemplate")
    public JdbcTemplate masterTemplate(@Qualifier("hiveDataSource") DataSource masterDataSource) {
        return new JdbcTemplate(masterDataSource);
    }

}
