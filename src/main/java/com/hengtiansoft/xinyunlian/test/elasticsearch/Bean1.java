package com.hengtiansoft.xinyunlian.test.elasticsearch;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Bean1 implements Serializable{


	private static final long serialVersionUID = -9105052283914758704L;

	private Long id;
	
	private String name;
	
	private String introduction;
	
	private Date crerateDate;
	
	private Float score;
	
	private Integer inventoryNum;
	
	private BigDecimal price;
	
	private Double cost;
	
	private Boolean isGift;

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
	
	
}
