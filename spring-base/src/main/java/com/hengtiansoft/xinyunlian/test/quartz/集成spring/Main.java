package com.hengtiansoft.xinyunlian.test.quartz.集成spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("quartz/springQuartz.xml");
	}
}
