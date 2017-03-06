package com.rujianbin.provider.mybatis.dao;

import org.apache.ibatis.annotations.Mapper;

import com.rujianbin.provider.mybatis.entity.Person;

@Mapper
public interface IPersonDao {

	Person queryByName(String name);
	
	Integer insert(Person person);
	
	Integer update(Person person);
}
