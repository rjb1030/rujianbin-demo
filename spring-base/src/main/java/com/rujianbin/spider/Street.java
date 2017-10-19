package com.rujianbin.spider;

public class Street {

	private String name;
	private String gbCode;
	
	public Street(String name,String gbCode){
		this.name=name;
		this.gbCode=gbCode;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGbCode() {
		return gbCode;
	}
	public void setGbCode(String gbCode) {
		this.gbCode = gbCode;
	}
	
	
}
