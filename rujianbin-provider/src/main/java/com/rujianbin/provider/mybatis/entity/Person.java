package com.rujianbin.provider.mybatis.entity;

import java.io.Serializable;

public class Person implements Serializable{

	private static final long serialVersionUID = -5884388026010229821L;
	private Integer id;
	private String name;
	private Integer age;
	
	public Person(){}
	
	public Person(String name,Integer age){
		this.name=name;
		this.age=age;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	
	
}
