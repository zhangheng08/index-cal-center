package com.gc.stcc.indexcal.sz.distcongest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Copyright (C) 2021, 华录高诚
 * @FileName: IndexDistCongestionApplication
 * @Author: ZhangHeng
 * @Date: 2021/7/21 11:51
 * @Description:
 * @Version:
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class, scanBasePackages = {"com.gc.stcc.indexcal.sz.distcongest", "com.gc.stcc.indexcal.core.config", "com.gc.stcc.indexcal.core.job"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = ("com.gc.stcc.indexcal.core.feign"))
public class IndexDistCongestionApplication {
    public static void main(String[] args) {
        SpringApplication.run(IndexDistCongestionApplication.class, args);
    }
}
