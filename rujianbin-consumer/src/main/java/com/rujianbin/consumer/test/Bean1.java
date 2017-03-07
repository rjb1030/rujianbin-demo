package com.rujianbin.consumer.test;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rujianbin.api.dto.PersonBean;

@Configuration
@EnableConfigurationProperties(TestProperties.class)
public class Bean1 {
	
	private TestProperties testProperties;
	
	public Bean1(TestProperties testProperties){
		this.testProperties=testProperties;
	}
	
	@Bean
	public PersonBean getPerson(TestProperties testProperties){
		PersonBean b = new PersonBean();
		b.setAge(testProperties.getAge());
		b.setName(testProperties.getName());
		return b; 
	}
	
}
