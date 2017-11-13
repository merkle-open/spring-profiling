/*
 * Copyright 2000-2015 Namics AG. All rights reserved.
 */

package com.namics.oss.spring.profiling.test.data;

import com.namics.oss.spring.profiling.annotation.Secret;
import org.springframework.stereotype.Component;

/**
 * SecretBean.
 *
 * @author aschaefer, Namics AG
 * @since 01.09.15 07:49
 */
@Component
public class SecretBean {

	public void namedParameter(String name, String password, String passwordHash, String newSecret) {
	}

	public void annotatedParameter(@Secret String name, String plain) {
	}

	@Secret
	public void annotatedMethod(String name) {
	}

	public void annotatedParameterClass(SecretClass param){

	}


}
