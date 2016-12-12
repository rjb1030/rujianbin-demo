package com.rujianbin.boot.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rujianbin.boot.service.IUserService;


@RestController
public class HellowController {

	@Resource(name="userServiceImpl")
	private IUserService userService;
	
	@RequestMapping("/hellow")  
    public String hellow(){  
		
        return "哈喽，Spring Boot ！-"+userService.getName();  
    }  

}
