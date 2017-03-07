package com.rujianbin.provider.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rujianbin.provider.mybatis.entity.Person;

@RestController
@RequestMapping("/provider")
public class ProviderController {

		@Autowired
		private RedisCacheManager manage;
	
		//127.0.0.1:7070/rujianbin-provider/provider/hellow
		@RequestMapping("/hellow")  
	    public String hellow(){  
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
}
