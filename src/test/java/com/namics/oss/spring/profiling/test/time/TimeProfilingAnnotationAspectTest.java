/*
 * Copyright 2000-2014 Namics AG. All rights reserved.
 */

package com.namics.oss.spring.profiling.test.time;

import com.namics.oss.spring.profiling.test.TestConfig;
import org.easymock.Capture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.easymock.EasyMock.*;

/**
 * DataProfilingAnnotationAspectTest.
 *
 * @author aschaefer
 * @since 30.01.14 11:00
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@EnableAspectJAutoProxy
class TimeProfilingAnnotationAspectTest {
	private static final Logger LOG = LoggerFactory.getLogger(TimeProfilingAnnotationAspectTest.class);

	@Autowired
	Logger logger;

	@Autowired
	TimeClassLevelAnnotationBean classLevel;

	@Autowired
	TimeMethodLevelAnnotationBean methodLevel;

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
		Capture<String> capture = Capture.newInstance();
		logger.debug(capture(capture));
		replay(logger);
		classLevel.someMethodTakingSomeTime();
		LOG.info(capture.getValue());
	}

	@Test
	void testMethodLevelTimeProfiling() {
		expect(logger.isDebugEnabled()).andReturn(true);
		Capture<String> capture = Capture.newInstance();
		logger.debug(capture(capture));
		replay(logger);
		methodLevel.someMethodTakingSomeTime();
		LOG.info(capture.getValue());
	}
}
