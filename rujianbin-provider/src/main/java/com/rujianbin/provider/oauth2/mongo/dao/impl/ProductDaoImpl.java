package com.rujianbin.provider.oauth2.mongo.dao.impl;

import com.rujianbin.provider.oauth2.mongo.dao.IProductDao;
import com.rujianbin.provider.oauth2.mongo.entity.Product;
import org.springframework.stereotype.Repository;

import com.rujianbin.provider.oauth2.mongo.dao.base.MongoBaseOperateDao;

@Repository
public class ProductDaoImpl extends MongoBaseOperateDao<Product> implements IProductDao<Product> {

	public ProductDaoImpl() {
		super();
	}
}
