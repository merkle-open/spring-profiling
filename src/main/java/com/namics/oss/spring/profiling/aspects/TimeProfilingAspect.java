/*
 * Copyright 2000-2014 Namics AG. All rights reserved.
 */

package com.namics.oss.spring.profiling.aspects;

import com.namics.oss.spring.profiling.profiler.TimeProfiler;
import org.aspectj.lang.annotation.Aspect;

/**
 * TimeProfilingAspect.
 *
 * @author aschaefer
 * @since 30.01.14 14:07
 */
@Aspect
public class TimeProfilingAspect extends TimeProfiler implements ProfilingAspect {
}
