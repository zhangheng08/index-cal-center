package com.gc.stcc.indexcal.manager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @Copyright (C) 2021, 华录高诚
 * @FileName: CorsConfig
 * @Author: ZhangHeng
 * @Date: 2021/7/15 10:17
 * @Description:
 * @Version:
 */
@Configuration
public class CorsConfig {

	@Bean
	public CorsFilter corsFilter() {
		final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		final CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.addAllowedOrigin("*");
		corsConfiguration.addAllowedHeader("*");
		corsConfiguration.addAllowedMethod("*");
		corsConfiguration.addExposedHeader("Access-Control-Allow-Origin"); 
		corsConfiguration.addExposedHeader("Access-Control-Allow-Methods");
		corsConfiguration.addExposedHeader("Access-Control-Allow-Headers");
		corsConfiguration.addExposedHeader("Access-Control-Max-Age");
		corsConfiguration.addExposedHeader("Access-Control-Request-Headers");
		corsConfiguration.addExposedHeader("Access-Control-Request-Method");
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}
}
