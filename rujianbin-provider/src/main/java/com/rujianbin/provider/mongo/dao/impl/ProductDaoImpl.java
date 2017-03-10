package com.rujianbin.provider.mongo.dao.impl;

import org.springframework.stereotype.Repository;

import com.rujianbin.provider.mongo.dao.IProductDao;
import com.rujianbin.provider.mongo.dao.base.MongoBaseOperateDao;
import com.rujianbin.provider.mongo.entity.Product;

@Repository
public class ProductDaoImpl extends MongoBaseOperateDao<Product> implements IProductDao<Product>{

	public ProductDaoImpl() {
		super();
	}
}
