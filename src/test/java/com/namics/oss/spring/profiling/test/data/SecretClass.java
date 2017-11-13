/*
 * Copyright 2000-2015 Namics AG. All rights reserved.
 */

package com.namics.oss.spring.profiling.test.data;

import com.namics.oss.spring.profiling.annotation.Secret;
import org.springframework.stereotype.Component;

/**
 * SecretClass.
 *
 * @author aschaefer, Namics AG
 * @since 01.09.15 08:54
 */
@Component
@Secret
public class SecretClass {

	public void test() {

	}
}
