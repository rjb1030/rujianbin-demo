package com.hengtiansoft.xinyunlian.test.spider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class City {

	private String name;
	private String gbCode;
	
	private Map<String,District> districtMap = new HashMap<String,District>();
	
	public City(String name,String gbCode){
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

	public Map<String, District> getDistrictMap() {
		return districtMap;
	}

	public void setDistrictMap(Map<String, District> districtMap) {
		this.districtMap = districtMap;
	}


	
	
}
