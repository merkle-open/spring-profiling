/*
 * Copyright 2000-2014 Namics AG. All rights reserved.
 */

package com.namics.oss.spring.profiling.config;

import com.namics.oss.spring.profiling.annotation.DataProfilingAnnotationAspect;
import com.namics.oss.spring.profiling.annotation.TimeProfilingAnnotationAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * ProfilingConfiguration.
 *
 * @author aschaefer
 * @since 30.01.14 14:12
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ProfilingConfiguration {
	@Bean
	public TimeProfilingAnnotationAspect timeProfilingAspect() {
		TimeProfilingAnnotationAspect timeProfilingAspect = new TimeProfilingAnnotationAspect();
		return timeProfilingAspect;
	}

	@Bean
	public DataProfilingAnnotationAspect dataProfilingAspect() {
		DataProfilingAnnotationAspect dataProfilingAspect = new DataProfilingAnnotationAspect();
		return dataProfilingAspect;
	}
}
