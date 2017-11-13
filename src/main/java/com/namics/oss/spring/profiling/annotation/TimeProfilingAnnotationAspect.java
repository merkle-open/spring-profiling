/*
 * Copyright 2000-2009 Namics AG. All rights reserved.
 */

package com.namics.oss.spring.profiling.annotation;

import com.namics.oss.spring.profiling.profiler.TimeProfiler;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Aspect for profiling execution time of methods.
 *
 * @author aschaefer, Namics AG
 * @since FFm 4.0.5 15.07.2013
 */
@Aspect
public class TimeProfilingAnnotationAspect extends TimeProfiler {

	@Pointcut("execution(@com.namics.oss.spring.profiling.annotation.TimeProfiling * *(..))")
	public void methodPointcut() {
	}

	@Pointcut("within(@com.namics.oss.spring.profiling.annotation.TimeProfiling *)")
	public void classPointcut() {
	}

	@Pointcut("execution(public * *(..))")
	public void publicMethod() {
	}

	/**
	 * Profiles a method call by logging data involved in method execution (parameters, return value , Throwable).
	 *
	 * @param pjp join point encapsulating the method
	 * @return result of method call
	 * @throws Throwable Throwable thrown at method call
	 */
	@Override
	@Around("methodPointcut() || ( publicMethod() && classPointcut())")
	public Object profile(ProceedingJoinPoint pjp) throws Throwable {
		return super.profile(pjp);
	}
}
