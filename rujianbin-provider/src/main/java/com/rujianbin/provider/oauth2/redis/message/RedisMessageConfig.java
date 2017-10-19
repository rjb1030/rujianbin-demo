package com.rujianbin.provider.oauth2.redis.message;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import com.google.common.collect.Lists;

@Configuration
@EnableConfigurationProperties(RedisMessageProperties.class)
public class RedisMessageConfig {

	private RedisMessageProperties redisMessageProperties;
	
	public RedisMessageConfig(RedisMessageProperties redisMessageProperties){
		this.redisMessageProperties=redisMessageProperties;
	}
	
	public RedisMessageProperties getRedisMessageProperties() {
		return redisMessageProperties;
	}

	public void setRedisMessageProperties(RedisMessageProperties redisMessageProperties) {
		this.redisMessageProperties = redisMessageProperties;
	}

	@Bean("channelTopic")
	public ChannelTopic getChannelTopic(){
		return new ChannelTopic(redisMessageProperties.getCacheMessageChannel());
	}
	
	@Bean("topicMessageHandle")
	public TopicMessageHandle getTopicMessageHandle(@Qualifier("redisTemplate") RedisTemplate redisTemplate){
		TopicMessageHandle handle = new TopicMessageHandle(redisTemplate);
		return handle;
	}
	
	@Bean("rjb-redisMessageListenerContainer")
	public RedisMessageListenerContainer getRedisMessageListenerContainer(@Qualifier("topicMessageHandle")TopicMessageHandle topicMessageHandle,
			@Qualifier("channelTopic")ChannelTopic channelTopic,
			@Qualifier("redisConnectionFactory")RedisConnectionFactory redisConnectionFactory){
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(redisConnectionFactory);
		Map listeners = new HashMap();
		listeners.put(topicMessageHandle, Lists.newArrayList(channelTopic));
		container.setMessageListeners(listeners);
		return container;
	}
	
	public static class TopicMessageHandle implements MessageListener{

		private RedisTemplate redisTemplate;
		
		public TopicMessageHandle(RedisTemplate redisTemplate){
			this.redisTemplate=redisTemplate;
		}
		
		@Override
		public void onMessage(Message message, byte[] pattern) {
			byte[] body = message.getBody();
			byte[] channel = message.getChannel();
			String m = (String)redisTemplate.getValueSerializer().deserialize(body);
			String topic = (String)redisTemplate.getStringSerializer().deserialize(channel);
			System.out.println("redis订阅-发布  topic="+topic);
			System.out.println("redis订阅-发布  message="+m);
		}

	
		
		
		
	}
}
