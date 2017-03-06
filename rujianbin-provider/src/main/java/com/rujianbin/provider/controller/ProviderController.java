package com.rujianbin.provider.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/provider")
public class ProviderController {

		//127.0.0.1:7070/rujianbin-provider/provider/hellow
		@RequestMapping("/hellow")  
	    public String hellow(){  
	        return "哈喽，Spring Boot ！- rujianbin-provider";  
	    }  
}
