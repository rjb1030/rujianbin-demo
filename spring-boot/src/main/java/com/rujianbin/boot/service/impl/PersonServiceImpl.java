package com.rujianbin.boot.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rujianbin.boot.entity.Person;
import com.rujianbin.boot.mapper.PersonMapper;
import com.rujianbin.boot.service.IPersonService;

@Service("personServiceImpl")
public class PersonServiceImpl implements IPersonService{

	@Resource(name="personMapper")
	PersonMapper personMapper;
	
	@Override
	public Person queryByName(String name) {
		return personMapper.queryByName(name);
	}

	@Override
	public Integer insert(Person person) {
		return personMapper.insert(person);
	}

	@Override
	public Integer update(Person person) {
		return personMapper.update(person);
	}

}
