package com.rujianbin.aop;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-aop.xml");
		IHuman human = ctx.getBean("yellowHuman",IHuman.class);
		System.out.println("==========================cry=============================");
		human.cry("cry");
		
		System.out.println("=========================talk==============================");
		String rs = human.talk("talk");
		System.out.println("参数返回值="+rs);
		
		System.out.println("=======================getDate===============================");
		human.getDate(new Date(), "yyyy-MM-dd HH:mm:ss", "getDate");
		
		System.out.println("=======================think================================");
		human.think("think");
		
	}
}
