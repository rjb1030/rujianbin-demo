package com.rujianbin.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 启动spring boot jar包 :java -jar xxx.jar
 * @author rujianbin
 *
 */
//@SpringBootApplication=@Configuration,@EnableAutoConfiguration,@ComponentScan
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages="com.rujianbin.boot")
public class Main {

	public static void main(String[] args) {  
        //第一个简单的应用，  
        SpringApplication.run(Main.class,args);  
  
    }  
}
