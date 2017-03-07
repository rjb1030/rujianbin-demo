package com.rujianbin.provider.redis.cluster;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 因默认的redisTemplate bean使用jdkSerializer. 故自定义redisTemplate bean   为了key的序列化
 * 同时session存储的template，  常规业务使用的template  以及StringRedisTemplate是同一个接口不同实现
 * @author rujianbin
 *
 */
@Configuration
public class RedisConfiguration {
	
	//常规的业务操作redistemplate
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate template = new RedisTemplate();
		template.setConnectionFactory(redisConnectionFactory);
		template.setKeySerializer(new GenericToStringSerializer(String.class));
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(JsonParser.Feature.IGNORE_UNDEFINED, true);
		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);
		serializer.setObjectMapper(objectMapper);
		template.setValueSerializer(serializer);
		template.setDefaultSerializer(serializer);
		template.setHashValueSerializer(serializer);
		template.afterPropertiesSet();
		return template;
	}
	
	/**
	 * 保存session的redisTemplate的默认序列化组件
	 * @param connectionFactory
	 * @return
	 * 2017年2月22日
	 * author rujianbin
	 */
	@Bean("springSessionDefaultRedisSerializer")
	public RedisSerializer sessionRedisTemplateSerializer(
			RedisConnectionFactory connectionFactory) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(JsonParser.Feature.IGNORE_UNDEFINED, true);
		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);
		serializer.setObjectMapper(objectMapper);
		
		return serializer;
	}
}
