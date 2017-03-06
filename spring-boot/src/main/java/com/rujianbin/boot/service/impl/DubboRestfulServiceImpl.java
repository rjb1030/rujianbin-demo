package com.rujianbin.boot.service.impl;

import org.springframework.stereotype.Component;

import com.rujianbin.boot.entity.Person;
import com.rujianbin.boot.service.DubboRestfulService;

@Component
public class DubboRestfulServiceImpl implements DubboRestfulService{

	@Override
	public String getName(String param, Long version) {
		
		return param+"___"+version;
	}

	@Override
	public Person updatePerson(Person p) {
		p.setName("has modify "+p.getName());
		return p;
	}

	@Override
	public String getAge() {
		return "1234";
	}

	





}
