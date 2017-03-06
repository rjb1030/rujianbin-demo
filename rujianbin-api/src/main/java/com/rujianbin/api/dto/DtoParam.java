package com.rujianbin.api.dto;

import java.io.Serializable;
import java.util.Date;

public class DtoParam implements Serializable{
	
	private static final long serialVersionUID = 1418463315377605323L;

	private Long id;

	private String name;
	
	private Integer age;
	
	private Date date;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
