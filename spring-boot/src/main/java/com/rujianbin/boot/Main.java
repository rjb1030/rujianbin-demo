package com.rujianbin.boot;

import java.util.concurrent.CountDownLatch;

import org.springframework.boot.builder.SpringApplicationBuilder;


public class Main {

	public static void main(String[] args) throws InterruptedException{  
        //第一个简单的应用，  
        new SpringApplicationBuilder().sources(Application.class,DubboApplication.class).web(false).run(args);
//        非web环境启动时，主线程会退出。需要阻塞主线程
        CountDownLatch closeLatch = new CountDownLatch(1);
        closeLatch.await();
    }  
}
