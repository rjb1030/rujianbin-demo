package com.rujianbin.boot;

import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动spring boot jar包 :java -jar xxx.jar
 * @author rujianbin
 *
 */
//@SpringBootApplication=@Configuration,@EnableAutoConfiguration,@ComponentScan(basePackages="com.rujianbin.boot")
@SpringBootApplication(scanBasePackages="com.rujianbin.boot")
public class Application {

}
