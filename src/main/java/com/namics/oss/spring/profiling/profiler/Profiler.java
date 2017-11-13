/*
 * Copyright 2000-2015 Namics AG. All rights reserved.
 */

package com.namics.oss.spring.profiling.profiler;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * Profiler.
 *
 * @author aschaefer, Namics AG
 * @since 31.08.15 13:12
 */
public interface Profiler {
	Object profile(final ProceedingJoinPoint pjp) throws Throwable;
}
