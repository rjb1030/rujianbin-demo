package com.rujianbin.provider.oauth2;

import org.apache.catalina.authenticator.jaspic.AuthConfigFactoryImpl;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import javax.security.auth.message.config.AuthConfigFactory;

/**
 * Created by rujianbin@xinyunlian.com on 2017/6/1.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println(DigestUtils.md5Hex("123456"));//和mysql 自身的md5加密一致
        //解决tomcat 版本太高，org.apache.catalina.authenticator.AuthenticatorBase.getJaspicProvider报错问题
        if (AuthConfigFactory.getFactory() == null) {
            AuthConfigFactory.setFactory(new AuthConfigFactoryImpl());
        }
        ApplicationContext ctx = new SpringApplicationBuilder().sources(
                Application.class).web(true).run(args);
//        CountDownLatch closeLatch = new CountDownLatch(1);
//        closeLatch.await();
    }
}
