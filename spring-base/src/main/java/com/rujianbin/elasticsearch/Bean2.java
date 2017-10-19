package com.rujianbin.elasticsearch;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

public class Bean2 implements Serializable{


	private static final long serialVersionUID = -7697452115308392348L;
	
	@Field(type = FieldType.String, index = FieldIndex.analyzed)
	private String country;
	
	@Field(type = FieldType.Long, index = FieldIndex.not_analyzed)
	private Long countryId;
	
	@Field(type = FieldType.Date, index = FieldIndex.not_analyzed)
	private Date createDate;

	@Field(type = FieldType.Nested)
	private Bean3 bean3;
	
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

	public Bean3 getBean3() {
		return bean3;
	}

	public void setBean3(Bean3 bean3) {
		this.bean3 = bean3;
	}
	
	
}
