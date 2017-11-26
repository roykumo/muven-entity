package com.eter.response.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.eter.response.CommonResponseGenerator;
import com.eter.response.filter.GlobalErrorHandler;

@Configuration
public class LoadResponseConfig {
	@Bean
	public CommonResponseGenerator commonResponseGenerator() {
		return new CommonResponseGenerator();
	}

	@Bean
	public GlobalErrorHandler globalErrorHandler( ) {
		return new GlobalErrorHandler();
	}

}
