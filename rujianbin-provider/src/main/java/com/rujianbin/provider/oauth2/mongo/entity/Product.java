package com.rujianbin.provider.oauth2.mongo.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="xinyunlian_product")
public class Product implements Serializable{

	private static final long serialVersionUID = 7441614987108350053L;

	@Id
	private String _id;
	
	private Long id;
	
	private String name;
	
	private Long categroyId;
	
	private String categoryName;
	
	List<ProductAttr> attrList = new ArrayList<ProductAttr>();
	
	
	public String get_id() {
		return _id;
	}


	public void set_id(String _id) {
		this._id = _id;
	}


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

	public Long getCategroyId() {
		return categroyId;
	}


	public void setCategroyId(Long categroyId) {
		this.categroyId = categroyId;
	}


	public String getCategoryName() {
		return categoryName;
	}


	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}


	public List<ProductAttr> getAttrList() {
		return attrList;
	}


	public void setAttrList(List<ProductAttr> attrList) {
		this.attrList = attrList;
	}


	public static class ProductAttr implements Serializable{
		
		private static final long serialVersionUID = -4502521442028474801L;

		private String attributeCode;
		
		private Long attributeId;
		
		private String attributeValue;
		
		public ProductAttr(){}
		
		public ProductAttr(String attributeCode,Long attributeId,String attributeValue){
			this.attributeCode=attributeCode;
			this.attributeId=attributeId;
			this.attributeValue=attributeValue;
		}

		public String getAttributeCode() {
			return attributeCode;
		}

		public void setAttributeCode(String attributeCode) {
			this.attributeCode = attributeCode;
		}

		public Long getAttributeId() {
			return attributeId;
		}

		public void setAttributeId(Long attributeId) {
			this.attributeId = attributeId;
		}

		public String getAttributeValue() {
			return attributeValue;
		}

		public void setAttributeValue(String attributeValue) {
			this.attributeValue = attributeValue;
		}
		
		
	}
	
	
}
