/*
 * Copyright 2000-2014 Namics AG. All rights reserved.
 */

package com.namics.oss.spring.profiling.test;

import com.namics.oss.spring.profiling.annotation.DataProfiling;
import com.namics.oss.spring.profiling.annotation.TimeProfiling;
import com.namics.oss.spring.profiling.config.EnableProfiling;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

/**
 * AutoConfigTest.
 *
 * @author aschaefer
 * @since 30.01.14 14:34
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AutoConfigTest.AutoConfig.class)
public class AutoConfigTest {
	@Configuration
	@EnableProfiling
	public static class AutoConfig {
		@Bean
		public TestBean testBean() {
			return new TestBean();
		}
	}

	public static class TestBean {
		@TimeProfiling
		@DataProfiling
		public String someMethod(String arg) {
			return arg;
		}
	}

	@Autowired
	TestBean testBean;

	@Test
	public void testProfiling() {
		assertEquals("test", testBean.someMethod("test"));
	}
}
