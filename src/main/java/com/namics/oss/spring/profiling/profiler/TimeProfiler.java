/*
 * Copyright 2000-2014 Namics AG. All rights reserved.
 */

package com.namics.oss.spring.profiling.profiler;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;

/**
 * TimeProfiler.
 *
 * @author aschaefer
 * @since 30.01.14 13:42
 */
public class TimeProfiler extends AbstractProfiler {

	/**
	 * Profiles a method call by logging needed time after method execution.
	 *
	 * @param pjp join point encapsulating the method
	 * @return result of method call
	 * @throws Throwable Throwable thrown at method call
	 */
	public Object profile(final ProceedingJoinPoint pjp) throws Throwable {
		Logger logger = getLogger(pjp);
		if (!logger.isDebugEnabled()) {
			return pjp.proceed();
		}

		final long start = System.nanoTime();
		Object returnValue;
		try {
			returnValue = pjp.proceed();
			return returnValue;
		} finally {
			final long end = System.nanoTime();
			final long durationMs = Math.round((end - start) / 1e6);
			final String className = pjp.getTarget().getClass().getName();
			final String methodSignature = pjp.getSignature().getName();
			logger.debug(String.format("%8d ms; %s.%s; %22d ns; %22d ns", durationMs, className, methodSignature, start, end));
		}
	}

}
