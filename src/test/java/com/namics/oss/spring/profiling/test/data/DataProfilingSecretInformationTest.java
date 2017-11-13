/*
 * Copyright 2000-2015 Namics AG. All rights reserved.
 */

package com.namics.oss.spring.profiling.test.data;

import com.namics.oss.spring.profiling.test.TestConfig;
import org.easymock.Capture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

/**
 * DataProfilingSecretInformationTest.
 *
 * @author aschaefer, Namics AG
 * @since 01.09.15 07:43
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class DataProfilingSecretInformationTest {

	@Autowired
	Logger logger;

	@Autowired
	SecretBean secretBean;

	@Autowired
	SecretClass secretClassBean;

	@Autowired
	TestDataProfilingAspect aspect;

	@Before
	public void resetMocks() {
		reset(logger);
	}

	@After
	public void verifyMocks() {
		verify(logger);
	}

	@Test
	public void testSecretParamAnnotation() {
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
	public void testSecretParamName() {
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
	public void testSecretMethodAnnotation() {
		expect(logger.isDebugEnabled()).andReturn(true);
		replay(logger);
		secretBean.annotatedMethod("name");
	}

	@Test
	public void testSecretParameterClassAnnotation() {
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
	public void testSecretClassAnnotation() {
		expect(logger.isDebugEnabled()).andReturn(true);
		replay(logger);
		secretClassBean.test();
	}

}
