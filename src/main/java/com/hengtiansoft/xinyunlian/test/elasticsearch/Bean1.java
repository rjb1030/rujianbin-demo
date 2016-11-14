package com.hengtiansoft.xinyunlian.test.elasticsearch;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "es-bean1",type = "es-bean1-type", shards = 1, replicas = 0)
public class Bean1 implements Serializable{


	private static final long serialVersionUID = -9105052283914758704L;

	@Id
	private Long id;
	
	@Field(type = FieldType.String, index = FieldIndex.analyzed)
	private String name;
	
	@Field(type = FieldType.String, index = FieldIndex.analyzed)
	private String introduction;
	
	@Field(type = FieldType.Date, index = FieldIndex.not_analyzed)
	private Date crerateDate;
	
	@Field(type = FieldType.Float, index = FieldIndex.not_analyzed)
	private Float score;
	
	@Field(type = FieldType.Integer, index = FieldIndex.not_analyzed)
	private Integer inventoryNum;
	
	@Field(type = FieldType.Double, index = FieldIndex.not_analyzed)
	private BigDecimal price;
	
	@Field(type = FieldType.Double, index = FieldIndex.not_analyzed)
	private Double cost;
	
	@Field(type = FieldType.Boolean, index = FieldIndex.not_analyzed)
	private Boolean isGift;

	@Field(type = FieldType.Nested)
	private Bean2 bean2;
	
	@Field(type = FieldType.Nested)
	private List<Bean2> bean2List;
	
	@Field(type = FieldType.Nested)
	private Map<String,Bean3> beansMap;
	
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

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public Date getCrerateDate() {
		return crerateDate;
	}

	public void setCrerateDate(Date crerateDate) {
		this.crerateDate = crerateDate;
	}

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public Integer getInventoryNum() {
		return inventoryNum;
	}

	public void setInventoryNum(Integer inventoryNum) {
		this.inventoryNum = inventoryNum;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Boolean getIsGift() {
		return isGift;
	}

	public void setIsGift(Boolean isGift) {
		this.isGift = isGift;
	}

	public Bean2 getBean2() {
		return bean2;
	}

	public void setBean2(Bean2 bean2) {
		this.bean2 = bean2;
	}

	public List<Bean2> getBean2List() {
		return bean2List;
	}

	public void setBean2List(List<Bean2> bean2List) {
		this.bean2List = bean2List;
	}

	public Map<String, Bean3> getBeansMap() {
		return beansMap;
	}

	public void setBeansMap(Map<String, Bean3> beansMap) {
		this.beansMap = beansMap;
	}
	
	
	
}
