package com.rujianbin.consumer.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rujianbin.api.DubboRestfulService;
import com.rujianbin.api.dto.PersonBean;

@RestController
@RequestMapping("/consumer")
public class ConsumerController {

	@Resource(name="dubboRestfulServiceDubboReferenceImpl")
	private DubboRestfulService DubboRestfulService;
	
	//127.0.0.1:7080/rujianbin-consumer/consumer/hellow
	@RequestMapping("/hellow")  
    public String hellow(){  
        return "哈喽，Spring Boot ！- rujianbin-consumer";  
    }
	
	@RequestMapping("/dubbo")  
	public PersonBean getDubbo(){
		return DubboRestfulService.getPersonBean("rjb");
	}
}
