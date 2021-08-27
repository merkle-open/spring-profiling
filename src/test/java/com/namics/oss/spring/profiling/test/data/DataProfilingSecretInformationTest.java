/*
 * Copyright 2000-2015 Namics AG. All rights reserved.
 */

package com.namics.oss.spring.profiling.test.data;

import com.namics.oss.spring.profiling.test.TestConfig;
import org.easymock.Capture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * DataProfilingSecretInformationTest.
 *
 * @author aschaefer, Namics AG
 * @since 01.09.15 07:43
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class DataProfilingSecretInformationTest {

	@Autowired
	Logger logger;

	@Autowired
	SecretBean secretBean;

	@Autowired
	SecretClass secretClassBean;

	@Autowired
	TestDataProfilingAspect aspect;

	@BeforeEach
	void resetMocks() {
		reset(logger);
	}

	@AfterEach
	void verifyMocks() {
		verify(logger);
	}

	@Test
	void testSecretParamAnnotation() {
		expect(logger.isDebugEnabled()).andReturn(true);
		Capture<Map> captured = newCapture();
		logger.debug(anyObject(String.class), anyObject(), capture(captured), anyObject(), anyObject());
		replay(logger);
		secretBean.annotatedParameter("name", "plain");

		Map args = captured.getValue();
		assertNotNull(args.get("name"));
		assertNotEquals(args.get("name"), "name");
		assertEquals(args.get("plain"), "plain");

	}

	@Test
	void testSecretParamName() {
		expect(logger.isDebugEnabled()).andReturn(true);
		Capture<Map> captured = newCapture();
		logger.debug(anyObject(String.class), anyObject(), capture(captured), anyObject(), anyObject());
		replay(logger);
		secretBean.namedParameter("name", "password", "passwordHash", "newSecret");

		Map args = captured.getValue();
		assertNotNull(args.get("name"));
		assertNotNull(args.get("password"));
		assertNotNull(args.get("passwordHash"));
		assertNotNull(args.get("newSecret"));

		assertEquals(args.get("name"), "name");
		assertNotEquals(args.get("password"), "plain");
		assertNotEquals(args.get("passwordHash"), "passwordHash");
		assertNotEquals(args.get("newSecret"), "newSecret");
	}

	@Test
	void testSecretMethodAnnotation() {
		expect(logger.isDebugEnabled()).andReturn(true);
		replay(logger);
		secretBean.annotatedMethod("name");
	}

	@Test
	void testSecretParameterClassAnnotation() {
		expect(logger.isDebugEnabled()).andReturn(true);
		Capture<Map> captured = newCapture();
		logger.debug(anyObject(String.class), anyObject(), capture(captured), anyObject(), anyObject());
		replay(logger);
		secretBean.annotatedParameterClass(new SecretClass());
		Map args = captured.getValue();
		assertNotNull(args.get("param"));
		assertFalse(args.get("param") instanceof SecretClass);
	}

	@Test
	void testSecretClassAnnotation() {
		expect(logger.isDebugEnabled()).andReturn(true);
		replay(logger);
		secretClassBean.test();
	}

}
