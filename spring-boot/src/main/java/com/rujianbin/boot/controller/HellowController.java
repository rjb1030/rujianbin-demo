package com.rujianbin.boot.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rujianbin.boot.entity.Person;
import com.rujianbin.boot.service.IPersonService;
import com.rujianbin.boot.service.IUserService;


@RestController
public class HellowController {

	@Resource(name="userServiceImpl")
	private IUserService userService;
	
	@Resource(name="personServiceImpl")
	private IPersonService personService;
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	//127.0.0.1:8080/spring-boot/hellow
	@RequestMapping("/hellow")  
    public String hellow(){  
		stringRedisTemplate.opsForValue().set("rujianbin-key", "rururu");
        return "哈喽，Spring Boot ！-"+userService.getName();  
    }  

	@RequestMapping("/person/{name}")  
    public String person(@PathVariable("name") String name){  
		Person p = personService.queryByName(name);
        return "哈喽，Spring Boot ！-"+p.getName()+",age="+p.getAge();  
    }
	
	@RequestMapping("/session")  
    public String session(HttpServletRequest request){  
		HttpSession session = request.getSession();
        return "session id ---->"+session.getId(); 
    }
	
	
	
}
