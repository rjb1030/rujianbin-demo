package com.rujianbin.provider.oauth2.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.rujianbin.provider.oauth2.mongo.dao.IProductDao;
import com.rujianbin.provider.oauth2.mongo.entity.Product;
import com.rujianbin.provider.oauth2.mybatis.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.CommandResult;
import com.rujianbin.provider.oauth2.redis.message.RedisMessageProperties;

@RestController
@RequestMapping("/provider")
public class ProviderController {

		@Autowired
		private RedisCacheManager manage;
		
		@Resource(name="redisTemplate")
		private RedisTemplate redisTemplate;
		
		@Autowired
		private RedisMessageProperties redisMessageProperties;
		
		@Resource(name="productDaoImpl")
		private IProductDao productDao;
	
		//127.0.0.1:7070/rujianbin-provider/provider/hellow
		@RequestMapping("/hellow")  
	    public String hellow(){ 
			Query query = new Query();
			List<Product> list =  productDao.find(query);
			CommandResult r = productDao.executeCommand("{categoryName:'葡萄酒'}");
			
	        return "哈喽，Spring Boot ！- rujianbin-provider";  
	    }  
		
		//127.0.0.1:7070/rujianbin-provider/provider/session
		@RequestMapping("/session")  
	    public String session(HttpServletRequest request){  
			HttpSession session = request.getSession();
			session.setAttribute("address", "三江口杨善路");
			session.setAttribute("person-info", new Person("张三",12));
			Cache cache = manage.getCache("namespace1");
			cache.put("my-current-date", System.currentTimeMillis());
	        return "session id ---->"+session.getId(); 
	    }
		
		
		//127.0.0.1:7070/rujianbin-provider/provider/redis-pub-sub
		@RequestMapping("/redis-pub-sub")
	    public String redisPubSub() {
			redisTemplate.convertAndSend(redisMessageProperties.getCacheMessageChannel(), "redis-topic-message");
	        return "true";
	    }
}
