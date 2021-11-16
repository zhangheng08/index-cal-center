package com.gc.stcc.indexcal.manager;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Payne08
 */
@SpringBootApplication(exclude = {DruidDataSourceAutoConfigure.class, DataSourceAutoConfiguration.class}, scanBasePackages = {"com.gc.stcc.indexcal.manager", "com.gc.stcc.indexcal.core.ds2.config"})
@EnableAsync
@EnableScheduling
@EnableEurekaClient
@EnableFeignClients
public class IndexCalculateManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(IndexCalculateManagerApplication.class, args);
    }

}
