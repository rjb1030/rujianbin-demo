package com.rujianbin.provider;

import org.springframework.boot.builder.SpringApplicationBuilder;

import com.rujianbin.api.ApiApplication;
import com.rujianbin.dubbo.autoconfiguration.DubboConfigApplication;
import com.rujianbin.freemark.autoconfiguration.FreemarkApplication;
import com.rujianbin.provider.mongo.MongoApplication;


public class Main {

	public static void main(String[] args) throws InterruptedException{
		new SpringApplicationBuilder().sources(
				Application.class,
				MongoApplication.class,
				ApiApplication.class,
				DubboConfigApplication.class,
				FreemarkApplication.class).web(true).run(args);
//        CountDownLatch closeLatch = new CountDownLatch(1);
//        closeLatch.await();
	}
}
