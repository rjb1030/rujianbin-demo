package com.rujianbin.provider.oauth2.mybatis.dao;

import com.rujianbin.provider.oauth2.mybatis.entity.Person;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IPersonDao {

	Person queryByName(String name);
	
	Integer insert(Person person);
	
	Integer update(Person person);
}
