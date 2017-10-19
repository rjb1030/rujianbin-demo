package com.rujianbin.aop;

public class AOPBean2 {

	public void beforeMethod(){
		System.out.println("@Before执行方法【前】切面逻辑2");
	}

	public void afterMethod(){
		System.out.println("@After执行方法【后】切面逻辑2");
	}
}
