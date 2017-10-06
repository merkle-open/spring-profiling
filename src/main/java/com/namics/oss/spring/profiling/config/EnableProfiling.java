/*
 * Copyright 2000-2014 Namics AG. All rights reserved.
 */

package com.namics.oss.spring.profiling.config;

import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(ProfilingConfigurationSelector.class)
public @interface EnableProfiling {

	/**
	 * Indicate how caching advice should be applied. The default is
	 * {@link org.springframework.context.annotation.AdviceMode#PROXY}.
	 *
	 * @see org.springframework.context.annotation.AdviceMode
	 */
	AdviceMode mode() default AdviceMode.ASPECTJ;
}
