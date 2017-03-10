package com.hengtiansoft.xinyunlian.test.mongo.impl;

import org.springframework.stereotype.Service;

import com.hengtiansoft.xinyunlian.test.mongo.MongoBaseOperateDao;
import com.hengtiansoft.xinyunlian.test.mongo.Person;

@Service("personImpl")
public class PersonImpl extends MongoBaseOperateDao<Person> {

	public PersonImpl() {
		super();
	}
	
}
