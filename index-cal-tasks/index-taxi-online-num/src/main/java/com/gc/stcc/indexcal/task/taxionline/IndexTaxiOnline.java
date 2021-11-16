package com.gc.stcc.indexcal.task.taxionline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Copyright (C) 2021, 华录高诚
 * @FileName: IndexTaxiOnlineNumApplication
 * @Author: ZhangHeng
 * @Date: 2021/6/29 16:19
 * @Description:
 * @Version:
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class, scanBasePackages = {"com.gc.stcc.indexcal.task.taxionline", "com.gc.stcc.indexcal.core.ds","com.gc.stcc.indexcal.core.config", "com.gc.stcc.indexcal.core.job"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = ("com.gc.stcc.indexcal.core.feign"))
public class IndexTaxiOnline {
    public static void main(String[] args) {
        SpringApplication.run(IndexTaxiOnline.class, args);
    }
}

