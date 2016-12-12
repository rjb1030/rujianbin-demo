package com.hengtiansoft.xinyunlian.test.aop2;

import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AOPBean {
	
	//配置切入点,该方法无方法体,主要为方便同类中其他方法使用此处配置的切入点
	@Pointcut("execution(* com.hengtiansoft.xinyunlian.test.aop2.YellowHuman.*(..))")
	public void aspect(){	}

	@Before("aspect()")
	public void beforeMethod(){
		System.out.println("@Before执行方法【前】切面逻辑");
	}

	@After("aspect()")
	public void afterMethod(){
		System.out.println("@After执行方法【后】切面逻辑");
	}

	@AfterReturning(returning="returning",pointcut="execution(* com.hengtiansoft.xinyunlian.test.aop2.YellowHuman.talk(..))")
	public void afterMethodUseReturn(String returning){
		System.out.println("@AfterReturning执行方法【后】切面逻辑，   目标方法返回值="+returning);
	}
	
	
	@AfterThrowing(throwing="ex",pointcut="execution(* com.hengtiansoft.xinyunlian.test.aop2.YellowHuman.think(..))")
	public void afterMethodThrowing(Throwable ex){
		System.out.println("@AfterThrowing执行方法【后】切面逻辑，   目标方法抛异常="+ex.getMessage());
	}
	
	
	@Around("execution(* com.hengtiansoft.xinyunlian.test.aop2.YellowHuman.talk(..))")
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
	
	@AfterReturning(returning="returning",pointcut="execution(* com.hengtiansoft.xinyunlian.test.aop2.YellowHuman.getDate(..)) && args(d,f,..)") 
	public void dealGetDate(Date d,String f,String returning){
		System.out.println("@AfterReturning(待参数或返回值限制)传入的Date="+d+",String="+f);
	}
}
