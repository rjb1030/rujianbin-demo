package com.rujianbin.aop;

import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;

@SuppressWarnings("all")
public class AOPBean {

	public void beforeMethod(){
		System.out.println("@Before执行方法【前】切面逻辑");
	}

	public void afterMethod(){
		System.out.println("@After执行方法【后】切面逻辑");
	}

	
	public void afterMethodUseReturn(String returning){
		System.out.println("@AfterReturning执行方法【后】切面逻辑，   目标方法返回值="+returning);
	}
	
	
	
	public void afterMethodThrowing(Throwable ex){
		System.out.println("@AfterThrowing执行方法【后】切面逻辑，   目标方法抛异常="+ex.getMessage());
	}
	
	
	
	public String around(ProceedingJoinPoint p){
		System.out.println("@Around方法执行【前】");
		try {
			//执行目标方法,原参数：p.getArgs()	  目标方法调用对象：p.getTarget()
			
			Object obj=p.proceed(new String[]{"新传入参数"});
		} catch (Throwable e) {
			e.printStackTrace();
		}
		System.out.println("@Around方法执行【后】");
		return "新返回参数";
	}
	
	public void dealGetDate(Date d,String f,String returning){
		System.out.println("@AfterReturning(待参数或返回值限制)传入的Date="+d+",String="+f);
	}
}
