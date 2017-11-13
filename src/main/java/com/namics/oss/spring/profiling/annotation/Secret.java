/*
 * Copyright 2000-2015 Namics AG. All rights reserved.
 */

package com.namics.oss.spring.profiling.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks secret information, so it should not be exposed in profiling, etc..
 * Can be applied to:
 * <ul>
 * <li>method parameter that should not be profiled</li>
 * <li>method that should not be profiled</li>
 * <li>class where methods should not be profiled</li>
 * <li>bean class that should not be profiled when used as parameter</li>
 * </ul>
 *
 * @author aschaefer, Namics AG
 * @since 31.08.15 16:31
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Secret {
}
