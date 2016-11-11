package com.hengtiansoft.xinyunlian.test.elasticsearch;

import java.io.Serializable;
import java.util.Date;

public class Bean2 implements Serializable{


	private static final long serialVersionUID = -7697452115308392348L;
	
	private String country;
	
	private Long countryId;
	
	private Date createDate;

	public Bean2(){}
	
	public Bean2(String country,Long countryId,Date createDate){
		this.country=country;
		this.countryId=countryId;
		this.createDate=createDate;
	}
	
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
}
