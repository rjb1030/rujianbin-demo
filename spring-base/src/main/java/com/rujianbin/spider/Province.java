package com.rujianbin.spider;

import java.util.HashMap;
import java.util.Map;

public class Province {

	private String name;
	private String gbCode;
	
	private Map<String,City> cityMap = new HashMap<String,City>();
	
	public Province(String name,String gbCode){
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

	public Map<String, City> getCityMap() {
		return cityMap;
	}

	public void setCityMap(Map<String, City> cityMap) {
		this.cityMap = cityMap;
	}


	
	
}
