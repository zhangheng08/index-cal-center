package com.gc.stcc.indexcal.manager.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Copyright (C) 2021, 华录高诚
 * @FileName: DwdDbType
 * @Author: ZhangHeng
 * @Date: 2021/8/2 10:12
 * @Description:
 * @Version:
 */
@Configuration
@ConfigurationProperties(prefix = "dwd.db")
@Data
public class DwdDbType {

    private String type;

}
