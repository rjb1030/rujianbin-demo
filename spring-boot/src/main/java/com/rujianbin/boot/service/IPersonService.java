package com.rujianbin.boot.service;

import com.rujianbin.boot.entity.Person;

public interface IPersonService {

	Person queryByName(String name);
	
	Integer insert(Person person);
	
	Integer update(Person person);
}
