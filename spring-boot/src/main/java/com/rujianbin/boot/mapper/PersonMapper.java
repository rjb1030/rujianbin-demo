package com.rujianbin.boot.mapper;

import org.springframework.stereotype.Repository;

import com.rujianbin.boot.entity.Person;

@Repository
public interface PersonMapper {

	Person queryByName(String name);
	
	Integer insert(Person person);
	
	Integer update(Person person);
}
