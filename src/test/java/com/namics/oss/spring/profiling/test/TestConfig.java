/*
 * Copyright 2000-2014 Namics AG. All rights reserved.
 */

package com.namics.oss.spring.profiling.test;

import com.namics.oss.spring.profiling.annotation.DataProfilingAnnotationAspect;
import com.namics.oss.spring.profiling.annotation.TimeProfilingAnnotationAspect;
import com.namics.oss.spring.profiling.test.data.TestDataProfilingAspect;
import org.easymock.EasyMock;
import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * TestConfig.
 *
 * @author aschaefer
 * @since 30.01.14 11:09
 */
@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "com.namics.oss.spring.profiling.test")
public class TestConfig {
	@Bean
	public Logger logger() {
		return EasyMock.createMock(Logger.class);
	}

	@Bean
	public TimeProfilingAnnotationAspect timeProfilingAspect() {
		TimeProfilingAnnotationAspect timeProfilingAspect = new TimeProfilingAnnotationAspect();
		timeProfilingAspect.setLogger(logger());
		return timeProfilingAspect;
	}

	@Bean
	public DataProfilingAnnotationAspect dataProfilingAnnotationAspect() {
		DataProfilingAnnotationAspect dataProfilingAspect = new DataProfilingAnnotationAspect();
		dataProfilingAspect.setLogger(logger());
		return dataProfilingAspect;
	}

	@Bean
	public TestDataProfilingAspect testDataProfilingAspect(){
		TestDataProfilingAspect aspect = new TestDataProfilingAspect();
		aspect.setLogger(logger());
		return aspect;
	}

}
