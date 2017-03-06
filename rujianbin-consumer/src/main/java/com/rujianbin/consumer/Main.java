package com.rujianbin.consumer;

import java.util.concurrent.CountDownLatch;

import org.springframework.boot.builder.SpringApplicationBuilder;

public class Main {

	public static void main(String[] args) throws InterruptedException{
		new SpringApplicationBuilder().sources(Application.class).web(false).run(args);
        CountDownLatch closeLatch = new CountDownLatch(1);
        closeLatch.await();
	}
}
