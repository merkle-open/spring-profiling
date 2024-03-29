/*
 * Copyright 2000-2014 Namics AG. All rights reserved.
 */

package com.namics.oss.spring.profiling.test;

import com.namics.oss.spring.profiling.annotation.DataProfiling;
import com.namics.oss.spring.profiling.annotation.TimeProfiling;
import com.namics.oss.spring.profiling.config.EnableProfiling;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * AutoConfigTest.
 *
 * @author aschaefer
 * @since 30.01.14 14:34
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AutoConfigTest.AutoConfig.class)
class AutoConfigTest {
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
	 void testProfiling() {
		assertEquals("test", testBean.someMethod("test"));
	}
}
