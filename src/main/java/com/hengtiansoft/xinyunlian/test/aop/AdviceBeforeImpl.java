package com.hengtiansoft.xinyunlian.test.aop;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;

public class AdviceBeforeImpl implements MethodBeforeAdvice,AfterReturningAdvice{

	@Override
	public void before(Method m, Object[] params, Object target)
			throws Throwable {
		System.out.println("advice before");
		
	}

	@Override
	public void afterReturning(Object paramObject1, Method paramMethod,
			Object[] paramArrayOfObject, Object target) throws Throwable {
		System.out.println("advice after");
		
	}

}
