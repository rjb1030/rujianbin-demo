package com.rujianbin.boot.redis.session;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 使用redis实现session共享，不配置本类则默认session失效时间是30分钟
 * @author rujianbin
 *
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 10)
public class SessionConfig {

}