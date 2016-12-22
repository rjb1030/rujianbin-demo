package com.hengtiansoft.xinyunlian.test.mongo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.google.common.collect.Lists;

public class Main {

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-mongo.xml");
	
		IMongoOperateDao operate = ctx.getBean("personImpl",IMongoOperateDao.class);
		Query query = new Query();
		Criteria criteria=Criteria.where("name").regex("rujianbin").and("age").in(Lists.newArrayList(132));  
		query.addCriteria(criteria); 
		Page<Person> page = operate.findPage(1, 20, query);

		if(page!=null && page.getContent()!=null && page.getContent().size()>0){
			for(Person p : page.getContent()){
				System.out.println("name="+p.getName()+",age="+p.getAge());
			}
		}else{
			System.out.println("无数据");
		}
	}
}
