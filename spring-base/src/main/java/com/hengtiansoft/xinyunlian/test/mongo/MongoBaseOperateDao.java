package com.hengtiansoft.xinyunlian.test.mongo;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

public class MongoBaseOperateDao<T> implements IMongoOperateDao<T>{

	private Class clazz;
	
	@SuppressWarnings("unchecked")
	public MongoBaseOperateDao() {
		clazz = getSuperClassGenricType(getClass());
	}
	
	@Autowired
	protected MongoTemplate mongoTemplate;
	
	
	
	@Override
	public List<T> find(Query query) {
		return (List<T>)mongoTemplate.find(query, clazz); 
	}

	@Override
	public T findOne(Query query) {
		return (T)mongoTemplate.findOne(query, clazz);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Page<T> findPage(Integer pageNo,Integer pageSize, Query query) {
		long count = this.count(query);  
		PageRequest pageable = new PageRequest(pageNo,pageSize);
        query.skip((pageNo - 1) * pageSize).limit(pageSize);  
        List<T> rows = this.find(query);  
        Page page = new PageImpl(rows,pageable,count);  
        return page;
	}

	@Override
	public long count(Query query) {
		return mongoTemplate.count(query, clazz);
	}

	
	@SuppressWarnings({ "unchecked", "rawtypes" })  
    public  Class getSuperClassGenricType(final Class clazz) {  
          
        //返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。  
		Type genType = clazz.getGenericSuperclass(); 
        //返回表示此类型实际类型参数的 Type 对象的数组。  
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();  
  
        return ((Class) params[0]);  
    }
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })  
    public  Class<Object> getSuperClassGenricType(final Class clazz, final int index) {  
          
        //返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。  
        Type genType = clazz.getGenericSuperclass();  
  
        if (!(genType instanceof ParameterizedType)) {  
           return Object.class;  
        }  
        //返回表示此类型实际类型参数的 Type 对象的数组。  
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();  
  
        if (index >= params.length || index < 0) {  
                     return Object.class;  
        }  
        if (!(params[index] instanceof Class)) {  
              return Object.class;  
        }  
  
        return (Class) params[index];  
    }  

}
