package com.rujianbin.provider.common;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by 汝建斌 on 2017/4/12.
 */
@Configuration
public class BeanConfig {

    @Bean("captchaProducer")
    public Producer captchaProducer(){

        Properties properties=null;

        try {
            InputStream stream = getClass().getResourceAsStream("/kaptcha.properties");
            properties = new Properties();
            properties.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
