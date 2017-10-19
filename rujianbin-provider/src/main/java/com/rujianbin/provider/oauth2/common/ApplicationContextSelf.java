package com.rujianbin.provider.oauth2.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by 汝建斌 on 2017/4/10.
 */
@Component
public class ApplicationContextSelf implements ApplicationContextAware{

    private static ApplicationContext ctx;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx = applicationContext;

    }

    public static <T> T getBean(String beanName,Class<T> classType){
        if(ctx==null){
            throw new RuntimeException("ApplicationContextSelf中ApplicationContext未正常注入");
        }
        return ctx.getBean(beanName,classType);
    }
}
