package com.hengtiansoft.xinyunlian.test.redis.springdata;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.hengtiansoft.xinyunlian.test.mq.TestBean;

@Component(value="redisMessageDelegateListener1")
public class RedisListenMessage1 {

	//监听什么方法 ，执行消息，可以xml里配置，默认handleMessage
	public void dealMyMessage(Serializable message){
		TestBean t = (TestBean)message;
		System.out.println("listener1 接收redis消息="+t.getName()+",age="+t.getAge());
	}
}
