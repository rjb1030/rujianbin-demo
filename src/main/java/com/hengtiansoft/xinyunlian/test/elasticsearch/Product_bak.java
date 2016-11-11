package com.hengtiansoft.xinyunlian.test.elasticsearch;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;
import org.wltea.analyzer.lucene.IKAnalyzer;

@Indexed
public class Product_bak implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2578234193734245988L;

	@DocumentId
	private Long id;
	
	@Field(store = Store.YES, index = Index.NO)
	private Date createDate;
	
	@Field(store = Store.YES, index = Index.NO)
	private Date modifyDate;
	
	//商品名称
	@Field(store = Store.YES, index = Index.TOKENIZED, analyzer = @Analyzer(impl = IKAnalyzer.class))
	private String name;
	
	//商品介绍
	@Field(store = Store.YES, index = Index.TOKENIZED, analyzer = @Analyzer(impl = IKAnalyzer.class))
	private String introduction;
	
	//售价
	@Field(store = Store.YES, index = Index.UN_TOKENIZED)
	private BigDecimal price;
	
	//成本
	@Field(store = Store.YES, index = Index.UN_TOKENIZED)
	private BigDecimal cost;
	
	//库存
	@Field(store = Store.YES, index = Index.UN_TOKENIZED)
	private Integer inventoryNum;
	
	//商品图片
	@Field(store = Store.YES, index = Index.NO)
	private String image;
	
	//是否礼品
	@Field(store = Store.YES, index = Index.UN_TOKENIZED)
	private boolean isGift;
	
	//是否上架
	@Field(store = Store.YES, index = Index.UN_TOKENIZED)
	private boolean isOnShelf;
	
	//评分
	@Field(store = Store.YES, index = Index.UN_TOKENIZED)
	private Float score;
	
	//点击数
	@Field(store = Store.YES, index = Index.UN_TOKENIZED)
	private Long hits;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public Integer getInventoryNum() {
		return inventoryNum;
	}

	public void setInventoryNum(Integer inventoryNum) {
		this.inventoryNum = inventoryNum;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public boolean isGift() {
		return isGift;
	}

	public void setGift(boolean isGift) {
		this.isGift = isGift;
	}

	public boolean isOnShelf() {
		return isOnShelf;
	}

	public void setOnShelf(boolean isOnShelf) {
		this.isOnShelf = isOnShelf;
	}

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public Long getHits() {
		return hits;
	}

	public void setHits(Long hits) {
		this.hits = hits;
	}
	
	
}
