package com.rujianbin.provider;

import org.springframework.boot.builder.SpringApplicationBuilder;

import com.rujianbin.dubbo.autoconfiguration.DubboConfigApplication;


public class Main {

	public static void main(String[] args) throws InterruptedException{
		new SpringApplicationBuilder().sources(DubboConfigApplication.class,Application.class).web(true).run(args);
//        CountDownLatch closeLatch = new CountDownLatch(1);
//        closeLatch.await();
	}
}
