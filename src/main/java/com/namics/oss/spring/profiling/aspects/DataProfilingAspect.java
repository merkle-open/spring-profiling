/*
 * Copyright 2000-2014 Namics AG. All rights reserved.
 */

package com.namics.oss.spring.profiling.aspects;

import com.namics.oss.spring.profiling.profiler.DataProfiler;
import org.aspectj.lang.annotation.Aspect;

/**
 * CAREFUL: DataProfiler may expose sensitive Data to logs.
 *
 * @author aschaefer
 * @since 30.01.14 14:07
 */
@Aspect
public class DataProfilingAspect extends DataProfiler implements ProfilingAspect {
}
