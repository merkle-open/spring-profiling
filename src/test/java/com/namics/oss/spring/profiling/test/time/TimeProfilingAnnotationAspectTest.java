/*
 * Copyright 2000-2014 Namics AG. All rights reserved.
 */

package com.namics.oss.spring.profiling.test.time;

import com.namics.oss.spring.profiling.test.TestConfig;
import org.easymock.Capture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.easymock.EasyMock.*;

/**
 * DataProfilingAnnotationAspectTest.
 *
 * @author aschaefer
 * @since 30.01.14 11:00
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@EnableAspectJAutoProxy
public class TimeProfilingAnnotationAspectTest {
	private static final Logger LOG = LoggerFactory.getLogger(TimeProfilingAnnotationAspectTest.class);

	@Autowired
	Logger logger;

	@Autowired
	TimeClassLevelAnnotationBean classLevel;

	@Autowired
	TimeMethodLevelAnnotationBean methodLevel;

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
		Capture<String> capture = new Capture<>();
		logger.debug(capture(capture));
		replay(logger);
		classLevel.someMethodTakingSomeTime();
		LOG.info(capture.getValue());
	}

	@Test
	public void testMethodLevelTimeProfiling() {
		expect(logger.isDebugEnabled()).andReturn(true);
		Capture<String> capture = new Capture<>();
		logger.debug(capture(capture));
		replay(logger);
		methodLevel.someMethodTakingSomeTime();
		LOG.info(capture.getValue());
	}
}
