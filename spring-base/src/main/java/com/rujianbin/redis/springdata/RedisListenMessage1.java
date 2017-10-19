package com.rujianbin.redis.springdata;

import java.io.Serializable;

import com.rujianbin.mq.TestBean;
import org.springframework.stereotype.Component;

@Component(value="redisMessageDelegateListener1")
public class RedisListenMessage1 {

	//监听什么方法 ，执行消息，可以xml里配置，默认handleMessage
	public void dealMyMessage(Serializable message){
		TestBean t = (TestBean)message;
		System.out.println("listener1 接收redis消息="+t.getName()+",age="+t.getAge());
	}
}
