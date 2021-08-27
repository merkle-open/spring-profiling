/*
 * Copyright 2000-2014 Namics AG. All rights reserved.
 */

package com.namics.oss.spring.profiling.test.data;

import com.namics.oss.spring.profiling.test.TestConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * DataProfilingAnnotationAspectTest.
 *
 * @author aschaefer
 * @since 30.01.14 11:00
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@EnableAspectJAutoProxy
class DataProfilingAnnotationAspectTest {

	@Autowired
	Logger logger;

	@Autowired
	DataClassLevelAnnotationBean classLevel;

	@Autowired
	DataMethodLevelAnnotationBean methodLevel;

	@BeforeEach
	void resetMocks() {
		reset(logger);
	}

	@AfterEach
	void verifyMocks() {
		verify(logger);
	}

	@Test
	void testClassLevelTimeProfiling() {
		expect(logger.isDebugEnabled()).andReturn(true);
		logger.debug(anyObject(String.class), anyObject(), anyObject(), anyObject(), anyObject());
		replay(logger);
		assertEquals("test", classLevel.someMethod("test"));
	}

	@Test
	void testMethodLevelTimeProfiling() {
		expect(logger.isDebugEnabled()).andReturn(true);
		logger.debug(anyObject(String.class), anyObject(), anyObject(), anyObject(), anyObject());
		replay(logger);
		assertEquals("test", methodLevel.someMethod("test"));
	}
}
