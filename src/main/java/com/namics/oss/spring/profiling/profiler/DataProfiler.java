/*
 * Copyright 2000-2014 Namics AG. All rights reserved.
 */

package com.namics.oss.spring.profiling.profiler;

import com.namics.oss.spring.profiling.annotation.Secret;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * CAREFUL: DataProfiler may expose sensitive Data to logs.
 *
 * @author aschaefer
 * @since 30.01.14 13:40
 */
public class DataProfiler extends AbstractProfiler {

	protected ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();
	protected Pattern secretNames = Pattern.compile("password|secret", Pattern.CASE_INSENSITIVE);
	protected String secretMask = "***";

	/**
	 * Profiles a method call by logging data involved in method execution (parameters, return value , Throwable).
	 *
	 * @param pjp join point encapsulating the method
	 * @return result of method call
	 * @throws Throwable Throwable thrown at method call
	 */
	public Object profile(final ProceedingJoinPoint pjp) throws Throwable {
		Logger logger = getLogger(pjp);
		if (!logger.isDebugEnabled()) {
			return pjp.proceed();
		}

		Object returnValue = null;
		Throwable thrown = null;
		try {
			returnValue = pjp.proceed();
			return returnValue;
		} catch (Throwable t) {
			thrown = t;
			throw t;
		} finally {
			Method method = method(pjp);
			if (shouldLog(method)) {
				logger.debug("{}({}) return {} throws {}", method.getName(), arguments(method, pjp), returnValue, thrown);
			}
		}
	}

	protected Method method(ProceedingJoinPoint pjp) {
		MethodSignature methodSignature = (MethodSignature) pjp.getStaticPart().getSignature();
		return methodSignature.getMethod();
	}

	protected boolean shouldLog(Method method) {
		if (method.getAnnotation(Secret.class) != null) {
			return false;
		}
		if (method.getDeclaringClass().getAnnotation(Secret.class) != null) {
			return false;
		}
		return true;
	}

	protected Map<String, Object> arguments(Method method, ProceedingJoinPoint pjp) {
		final Object[] args = pjp.getArgs();
		if (args == null || args.length < 1) {
			return Collections.emptyMap();
		}

		Map<String, Object> arguments = new HashMap<>(args.length);

		Annotation[][] parameterAnnotations = method.getParameterAnnotations();
		String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);

		for (int argIndex = 0; argIndex < args.length; argIndex++) {
			String name = parameterNames != null ? parameterNames[argIndex] : String.valueOf(argIndex);
			Object paramValue = args[argIndex];

			if (isParamSecret(parameterAnnotations[argIndex], name, paramValue)) {
				arguments.put(name, secretMask);
			} else {
				arguments.put(name, paramValue);
			}
		}

		return arguments;
	}

	protected boolean isParamSecret(Annotation[] annotations, String name, Object param) {
		return isParamNameSecret(name)
		       || isParamAnnotatedSecret(annotations)
		       || isParamClassAnnotatedSecret(param);
	}

	protected boolean isParamAnnotatedSecret(Annotation[] parameterAnnotation) {
		for (Annotation annotation : parameterAnnotation) {
			if (annotation instanceof Secret) {
				return true;
			}
		}
		return false;
	}

	protected boolean isParamClassAnnotatedSecret(Object parameter) {
		return parameter != null && parameter.getClass().getAnnotation(Secret.class) != null;
	}

	protected boolean isParamNameSecret(String name) {
		return secretNames.matcher(name).find();
	}

	public void setParameterNameDiscoverer(ParameterNameDiscoverer parameterNameDiscoverer) {
		this.parameterNameDiscoverer = parameterNameDiscoverer;
	}

	public DataProfiler parameterNameDiscoverer(ParameterNameDiscoverer parameterNameDiscoverer) {
		setParameterNameDiscoverer(parameterNameDiscoverer);
		return this;
	}

	public void setSecretNames(Pattern secretNames) {
		this.secretNames = secretNames;
	}

	public DataProfiler secretNames(Pattern secretNames) {
		setSecretNames(secretNames);
		return this;
	}

	public void setSecretMask(String secretMask) {
		this.secretMask = secretMask;
	}

	public DataProfiler secretMask(String secretMask) {
		setSecretMask(secretMask);
		return this;
	}
}
