package com.rujianbin.provider;

import javax.security.auth.message.config.AuthConfigFactory;

import org.apache.catalina.authenticator.jaspic.AuthConfigFactoryImpl;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.boot.builder.SpringApplicationBuilder;

import com.rujianbin.api.ApiApplication;
import com.rujianbin.dubbo.autoconfiguration.DubboConfigApplication;
import com.rujianbin.freemark.autoconfiguration.FreemarkApplication;
import com.rujianbin.provider.mongo.MongoApplication;
import org.springframework.context.ApplicationContext;

import java.time.Clock;

//如果jar包在window下运行，dos如果打印中文乱码，可以将dos的编码格式切为utf-8
public class Main {

	public static void main(String[] args) throws InterruptedException{

		System.out.println(DigestUtils.md5Hex("123456"));//和mysql 自身的md5加密一致
		//解决tomcat 版本太高，org.apache.catalina.authenticator.AuthenticatorBase.getJaspicProvider报错问题
        if (AuthConfigFactory.getFactory() == null) {
            AuthConfigFactory.setFactory(new AuthConfigFactoryImpl());
        }
		ApplicationContext ctx = new SpringApplicationBuilder().sources(
				Application.class,
				MongoApplication.class,
				ApiApplication.class,
				DubboConfigApplication.class,
				FreemarkApplication.class).web(true).run(args);
//        CountDownLatch closeLatch = new CountDownLatch(1);
//        closeLatch.await();
	}
}
