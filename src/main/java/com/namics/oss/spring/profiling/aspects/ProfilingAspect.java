/*
 * Copyright 2000-2015 Namics AG. All rights reserved.
 */

package com.namics.oss.spring.profiling.aspects;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * ProfilingAspect.
 *
 * @author aschaefer, Namics AG
 * @since 31.08.15 13:13
 */
public interface ProfilingAspect {
	Object profile(final ProceedingJoinPoint pjp) throws Throwable;
}
