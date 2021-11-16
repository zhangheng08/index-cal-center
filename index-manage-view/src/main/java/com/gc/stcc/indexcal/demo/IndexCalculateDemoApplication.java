package com.gc.stcc.indexcal.demo;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Copyright (C), 2015-2021, XXX有限公司
 * FileName: IndexCalculteDemoApplication
 * Author:  Payne08
 * Date: 2021/6/1 15:08
 * Description: index calculte demo
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名  修改时间  版本号 描述
 * @author Payne08
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DruidDataSourceAutoConfigure.class})
@EnableDiscoveryClient
@EnableFeignClients
public class IndexCalculateDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(IndexCalculateDemoApplication.class, args);
    }
}
