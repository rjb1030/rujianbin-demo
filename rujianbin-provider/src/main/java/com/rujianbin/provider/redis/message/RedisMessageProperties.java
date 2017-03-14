package com.rujianbin.provider.redis.message;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="spring.message")
public class RedisMessageProperties {

	private String cacheMessageChannel;

	public String getCacheMessageChannel() {
		return cacheMessageChannel;
	}

	public void setCacheMessageChannel(String cacheMessageChannel) {
		this.cacheMessageChannel = cacheMessageChannel;
	}
	
	
}
