package com.hengtiansoft.xinyunlian.test.mq;

import java.io.Serializable;

public class TestBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6285582520061878207L;
	
	private String name;
	private Integer age;
	
	public TestBean(String name,Integer age){
		this.name=name;
		this.age=age;
	}
	
	public TestBean(){
		
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
