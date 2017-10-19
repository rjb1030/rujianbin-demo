package com.rujianbin.spider;

import java.util.HashMap;
import java.util.Map;

public class District {

	private String name;
	private String gbCode;
	private Map<String,Street> streets = new HashMap<String,Street>();;
	
	public District(String name,String gbCode){
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

	public Map<String, Street> getStreets() {
		return streets;
	}

	public void setStreets(Map<String, Street> streets) {
		this.streets = streets;
	}
	
	
	
	
}
