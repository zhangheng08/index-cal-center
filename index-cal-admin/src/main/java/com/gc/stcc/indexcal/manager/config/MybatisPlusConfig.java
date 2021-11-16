package com.gc.stcc.indexcal.manager.config;

import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Copyright (C) 2021, 华录高诚
 * @FileName: MybatisPlusConfig
 * @Author: ZhangHeng
 * @Date: 2021/7/15 10:17
 * @Description:
 * @Version:
 */
@EnableTransactionManagement
@Configuration
@MapperScan("com.gc.stcc.indexcal.manager.model.mapper")
public class MybatisPlusConfig {

	@Bean
	public PaginationInterceptor paginationInterceptor() {
		PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
		paginationInterceptor.setOverflow(true);
		paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize());
		return paginationInterceptor;
	}

	@Bean
	public OptimisticLockerInterceptor optimisticLockerInterceptor() {
	    return new OptimisticLockerInterceptor();
	}

	@Bean
    public ISqlInjector sqlInjector() {
        return new DefaultSqlInjector();
    }

}
