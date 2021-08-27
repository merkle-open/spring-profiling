# Spring Profiling

System        | Status
--------------|------------------------------------------------        
CI master     | [![Build Status][travis-master]][travis-url]
CI develop    | [![Build Status][travis-develop]][travis-url]
Dependency    | [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.namics.oss.spring.profiling/spring-profiling/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.namics.oss.spring.profiling/spring-profiling)

The module provides tools to add profiling aspects to an application.

	- TimeProfiling
	- DataProfiling
	
The module uses @AspectJ and Spring provides configuration help for simplified configuration. 

## Usage

### Maven Dependency (Latest Version in `pom.xml`):

	<dependency>
		<groupId>com.namics.oss.spring.profiling</groupId>
		<artifactId>spring-profiling</artifactId>
		<version>1.1.0</version>
	</dependency>
	
### Requirements	

Java: JDK 8            	 


### Usage options

Although the module is basically independent of Spring but is in our environment the most frequent application.
The documentation of the use with other products can be supplemented at the given time.

### Security-Warning Data-Profiling

The `DataProfiler` logs all parameters, return values and exceptions of the method call!
This can cause sensitive data, such as passwords, to end up in the log files.
 
The following mechanisms are available to prevent unwanted data in the logs:

1. Based on parameter names values are masked
	- Default: parameter name contains "password" or "secret"
	- __Attention__ The code must be available for parameter names at runtime
		- compiling with debug option ( `javac -g`, IDE function, Maven(default), Gradle,...)
		- From Java 8 with parameter option (`javac -parameters`)
2. `@Secret` Annotation
	- As parameter annotation: The parameter is masked
	- As type annotation on the class of the parameter: The parameter is masked
	- As method annotation on a Spring-Bean method: the method is not logged
	- As type annotation on a spring bean: methods of bean are not logged	 			

### Spring Configuration

It is an advantage to look at the [AOP chapter] of the [Spring Reference Documentation].

[AOP chapter]: http://docs.spring.io/spring/docs/4.0.x/spring-framework-reference/htmlsingle/#aop-introduction
[Spring Reference Documentation]: http://docs.spring.io/spring/docs/4.0.x/spring-framework-reference/htmlsingle

Required Dependency

	<dependency>
		<groupId>org.springframework</groupId>
		<artifactId>spring-context</artifactId>
		<version>1.1.0</version>
	</dependency>

Profiling to non-public methods is only possible with compile or loadtime weaving (see AspectJ and CGLIB).

#### Annotations-based "local" Profiling

__Function:__ Individual methods / classes can be annotated for profiling

Turn on the profiling aspects

	@Configuration
	@EnableProfiling
	public static class Config
	{

	}

With `@Profile` annotation it is possible to switch the profiling on / off for specific application profiles.

Annotate methods or classes for which profiling is desired.

- Profiling is active on all public methods of an annotated class
- Profiling is active on all annotated methods

Sample:

	@TimeProfiling
	@DataProfiling
	public static class TypeAnnotatedBean
	{
		public String someMethod(String arg)
		{
			return arg;
		}
	}

	public static class MethodAnnotatedBean
	{
		@TimeProfiling
		@DataProfiling
		public String someAnnotatedMethod(String arg)
		{
			return arg;
		}
	}


#### "Global" Profiling with Annotation / JavaConfig

__Function:__ Multiple Spring-Bean methods can be captured using a PointCut.


Extend the required profiler and annotate the override `profile` method with a `@Around` advice.
In the example, all public methods in the project package:

	@Aspect
	public class TestDataProfilingAspect extends DataProfiler {
		@Override
		@Around("execution(public * com.namics.customer.project..*(..))")
		public Object profile(ProceedingJoinPoint pjp) throws Throwable {
			return super.profile(pjp);
		}
	}

Create an aspect bean in the context (`@Component` or` @Bean` method), avoid cyclic dependencies (the aspect itself should not be captured by the PointCut)


#### "Global" Profiling Aspect with XML-Namespace

__Function:__ Multiple Spring bean methods can be captured using a PointCut.

Required Dependency

        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-aop</artifactId>
            <version>1.1.0</version>
        </dependency>

Configuration

	<beans xmlns="http://www.springframework.org/schema/beans"
		xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		xmlns:aop="http://www.springframework.org/schema/aop"
		xsi:schemaLocation="http://www.springframework.org/schema/beans
		   http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
		   http://www.springframework.org/schema/aop
		   http://www.springframework.org/schema/aop/spring-aop-4.0.xsd">

		<bean id="dataProfilingBean" class="DataProfilingAspect" />
		<bean id="timeProfilingBean" class="TimeProfilingAspect" />

		<aop:config>
			<aop:aspect id="dataProfilingAspect" ref="dataProfilingBean">
				<aop:pointcut id="dataProfilingAspectPointcut" expression="execution(* com.namics.commons.*.*(..))" />
				<aop:around pointcut-ref="dataProfilingAspectPointcut" method="profile" />
			</aop:aspect>

			<aop:aspect id="timeProfilingAspect" ref="timeProfilingBean">
				<aop:pointcut id="timeProfilingAspectPointcut" expression="execution(* com.namics.commons.*.*(..))" />
				<aop:around pointcut-ref="timeProfilingAspectPointcut" method="profile" />
			</aop:aspect>
		</aop:config>
	</beans>


[travis-master]: https://travis-ci.org/namics/spring-profiling.svg?branch=master
[travis-develop]: https://travis-ci.org/namics/spring-profiling.svg?branch=develop
[travis-url]: https://travis-ci.org/namics/spring-profiling
