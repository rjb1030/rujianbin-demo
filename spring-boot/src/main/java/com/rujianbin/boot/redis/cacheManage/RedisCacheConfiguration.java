//package com.rujianbin.boot.redis.cacheManage;
//
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Import;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.core.RedisTemplate;
//
//import com.rujianbin.boot.redis.cacheManage.RedisCacheConfiguration.CacheExpires;
//import com.rujianbin.boot.redis.cluster.RedisConfiguration;
//
//@Configuration
//@EnableConfigurationProperties({ CacheExpires.class })
//@Import({ RedisConfiguration.class })
//public class RedisCacheConfiguration {
//
//	@Autowired
//	CacheExpires cacheExpires;
//
//	@Bean
//	public RedisCacheManager cacheManager(RedisTemplate<Object, Object> redisTemplate) {
//		RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
//		cacheManager.setUsePrefix(true);
////		cacheManager.setExpires(this.cacheExpires.getCacheExpires());
//		return cacheManager;
//	}
//
//	@ConfigurationProperties(prefix = "spring.cache")
//	public class CacheExpires {
//		Map<String, Long> cacheExpires;
//
//		public CacheExpires() {
//		}
//
//		public void setCacheExpires(String str) {
//			
//			System.out.println(str);
////			this.cacheExpires = ;
//		}
//
//		public Map<String, Long> getCacheExpires() {
//			return this.cacheExpires;
//		}
//		
//	}
//}
