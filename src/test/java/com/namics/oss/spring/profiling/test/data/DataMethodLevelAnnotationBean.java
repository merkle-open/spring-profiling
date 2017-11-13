/*
 * Copyright 2000-2014 Namics AG. All rights reserved.
 */

package com.namics.oss.spring.profiling.test.data;

import com.namics.oss.spring.profiling.annotation.DataProfiling;
import org.springframework.stereotype.Component;

/**
 * TestClassLevelAnnotationBean.
 *
 * @author aschaefer
 * @since 30.01.14 11:07
 */
@Component
public class DataMethodLevelAnnotationBean {
	@DataProfiling
	public String someMethod(String someArg) {
		return someArg;
	}
}
