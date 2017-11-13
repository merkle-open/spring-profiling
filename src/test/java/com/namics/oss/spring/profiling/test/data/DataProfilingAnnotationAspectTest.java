/*
 * Copyright 2000-2014 Namics AG. All rights reserved.
 */

package com.namics.oss.spring.profiling.test.data;

import com.namics.oss.spring.profiling.test.TestConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

/**
 * DataProfilingAnnotationAspectTest.
 *
 * @author aschaefer
 * @since 30.01.14 11:00
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@EnableAspectJAutoProxy
public class DataProfilingAnnotationAspectTest {

	@Autowired
	Logger logger;

	@Autowired
	DataClassLevelAnnotationBean classLevel;

	@Autowired
	DataMethodLevelAnnotationBean methodLevel;

	@Before
	public void resetMocks() {
		reset(logger);
	}

	@After
	public void verifyMocks() {
		verify(logger);
	}

	@Test
	public void testClassLevelTimeProfiling() {
		expect(logger.isDebugEnabled()).andReturn(true);
		logger.debug(anyObject(String.class), anyObject(), anyObject(), anyObject(), anyObject());
		replay(logger);
		assertEquals("test", classLevel.someMethod("test"));
	}

	@Test
	public void testMethodLevelTimeProfiling() {
		expect(logger.isDebugEnabled()).andReturn(true);
		logger.debug(anyObject(String.class), anyObject(), anyObject(), anyObject(), anyObject());
		replay(logger);
		assertEquals("test", methodLevel.someMethod("test"));
	}
}
