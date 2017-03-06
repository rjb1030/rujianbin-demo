package com.rujianbin.boot.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/**
 * 会在SpringApplication.run(…)完成之前被调用。适用于系统初始化配置的加载，启动检查等等
 * @author rujianbin
 *
 */
@Order(1)
@Component
public class Runner1 implements ApplicationRunner{

	private static final Logger logger = LoggerFactory.getLogger(Runner1.class);
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		logger.info("spring-boot启动 前  执行逻辑1。。。。。");
		
	}

}
