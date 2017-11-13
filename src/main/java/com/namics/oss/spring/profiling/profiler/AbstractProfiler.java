/*
 * Copyright 2000-2014 Namics AG. All rights reserved.
 */

package com.namics.oss.spring.profiling.profiler;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AbstractProfiler.
 *
 * @author aschaefer
 * @since 30.01.14 13:46
 */
public abstract class AbstractProfiler implements Profiler {
	/**
	 * Used logger instance.
	 */
	private Logger logger;

	abstract public Object profile(final ProceedingJoinPoint pjp) throws Throwable;

	protected Logger getLogger(ProceedingJoinPoint pjp) {
		Logger actualLogger = this.logger;
		// use actualLogger instance to be able to configure logging
		if (actualLogger == null) {
			// if no actualLogger is configured, log with profiled classes actualLogger
			actualLogger = LoggerFactory.getLogger(pjp.getTarget().getClass());
		}
		return actualLogger;
	}


	/**
	 * Getter for logger. @return the logger
	 */
	public Logger getLogger() {
		return this.logger;
	}

	/**
	 * Setter for logger. @param logger the logger to set
	 */
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	/**
	 * Setter for logger. @param logger the logger to set
	 */
	public void setLoggerClassName(Class<?> logger) {
		this.logger = LoggerFactory.getLogger(logger);
	}

	/**
	 * Setter for logger. @param logger the logger to set
	 */
	public void setLoggerName(String logger) {
		this.logger = LoggerFactory.getLogger(logger);
	}


}
