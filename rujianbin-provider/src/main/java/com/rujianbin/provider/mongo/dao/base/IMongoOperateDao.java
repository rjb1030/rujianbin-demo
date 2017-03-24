package com.rujianbin.provider.mongo.dao.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.mongodb.CommandResult;


public interface IMongoOperateDao<T> {

	
	public CommandResult executeCommand(String jsonCommand);
	
	public boolean save(T entity);
	
	
	/** 
     * 通过条件查询实体(集合) 
     *  
     * @param query 
     */  
    public List<T> find(Query query) ;  
  
    /** 
     * 通过一定的条件查询一个实体 
     *  
     * @param query 
     * @return 
     */  
    public T findOne(Query query) ; 
    
    /** 
     * 分页查询 
     * @param page 
     * @param query 
     * @return 
     */  
    public Page<T> findPage(Integer pageNo,Integer pageSize, Query query);  
      
    /** 
     * 求数据总和 
     * @param query 
     * @return 
     */  
    public long count(Query query);  
}
