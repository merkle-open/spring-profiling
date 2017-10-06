/*
 * Copyright 2000-2014 Namics AG. All rights reserved.
 */

package com.namics.oss.spring.profiling.test.time;

import com.namics.oss.spring.profiling.annotation.TimeProfiling;
import org.springframework.stereotype.Component;

/**
 * TestClassLevelAnnotationBean.
 *
 * @author aschaefer
 * @since 30.01.14 11:07
 */
@Component
@TimeProfiling
public class TimeClassLevelAnnotationBean {
	public void someMethodTakingSomeTime() {

	}
}
