package com.rujianbin.boot.redis.cacheManage;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * 启动cacheManage管理命名空间 spring.cache.cache-names以及加上@EnableCaching
 * 具体啥类无所谓，@EnableCaching可以加在任何类上.没有这个配置cacheManage不会实例化bean,同时也表示缓存注解开启开关，等同于<cache:annotation-driven /> 
 * @author rujianbin
 *
 */
@Configuration
@EnableCaching
public class CacheManageConfiguration {

}
