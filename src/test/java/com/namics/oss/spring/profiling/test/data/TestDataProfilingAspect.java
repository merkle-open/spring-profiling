/*
 * Copyright 2000-2015 Namics AG. All rights reserved.
 */

package com.namics.oss.spring.profiling.test.data;

import com.namics.oss.spring.profiling.profiler.DataProfiler;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * TestDataProfilingAspect.
 *
 * @author aschaefer, Namics AG
 * @since 01.09.15 08:04
 */
@Aspect
public class TestDataProfilingAspect extends DataProfiler {
	@Override
	@Around("execution(public * com.namics.oss.spring.profiling.test.data.Secret*.*(..))")
	public Object profile(ProceedingJoinPoint pjp) throws Throwable {
		return super.profile(pjp);
	}

}
