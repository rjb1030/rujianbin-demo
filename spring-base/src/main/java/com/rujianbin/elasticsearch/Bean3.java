package com.rujianbin.elasticsearch;

import java.io.Serializable;

public class Bean3 implements Serializable{

	private static final long serialVersionUID = 4760275090256166288L;

	private String name;
	
	private Integer age;

	public Bean3(){}
	
	public Bean3(String name,Integer age){
		this.name=name;
		this.age=age;
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
