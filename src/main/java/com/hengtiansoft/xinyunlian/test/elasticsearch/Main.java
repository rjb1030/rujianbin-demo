package com.hengtiansoft.xinyunlian.test.elasticsearch;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

public class Main {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		ElasticsearchTemplate template = context.getBean("elasticsearchTemplate", ElasticsearchTemplate.class);
		
		
		
		
	}
}
