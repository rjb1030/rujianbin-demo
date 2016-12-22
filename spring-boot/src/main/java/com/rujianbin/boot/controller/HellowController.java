package com.rujianbin.boot.controller;

import javax.annotation.Resource;

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
	
	//127.0.0.1:8080/hellow
	@RequestMapping("/hellow")  
    public String hellow(){  
        return "哈喽，Spring Boot ！-"+userService.getName();  
    }  

	@RequestMapping("/person/{name}")  
    public String person(@PathVariable("name") String name){  
		Person p = personService.queryByName(name);
        return "哈喽，Spring Boot ！-"+p.getName()+",age="+p.getAge();  
    }
	
}
