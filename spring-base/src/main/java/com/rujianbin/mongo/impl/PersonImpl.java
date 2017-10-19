package com.rujianbin.mongo.impl;

import com.rujianbin.mongo.MongoBaseOperateDao;
import org.springframework.stereotype.Service;

import com.rujianbin.mongo.Person;

@Service("personImpl")
public class PersonImpl extends MongoBaseOperateDao<Person> {

	public PersonImpl() {
		super();
	}
	
}
