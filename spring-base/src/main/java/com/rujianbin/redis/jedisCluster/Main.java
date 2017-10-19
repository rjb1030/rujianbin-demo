package com.rujianbin.redis.jedisCluster;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.JedisCluster;

/**
 * 队列模式：1条消息一个消费方
 * jedisCluster集群
 * @author 汝建斌
 *
 */
public class Main {

	public static void main(String[] args) {
		
		//redis的list模拟队列
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

		final JedisCluster jedisCluster = context.getBean("jedisCluster", JedisCluster.class);
		jedisCluster.set("hello virtualbox", "hello virtualbox");
		ExecutorService threadPool = Executors.newFixedThreadPool(1000);
		
		for(int i=0;i<10;i++){
			threadPool.execute(new Runnable(){
				@Override
				public void run(){
					while(true){
						Object value = jedisCluster.rpop("testkey");
						if(value!=null){
							System.out.println(value);
						}
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
						}
						
						
					}
				}
			});
		}
		
		final AtomicInteger at = new AtomicInteger(0);
		for(int i=0;i<10;i++){
			threadPool.execute(new Runnable(){
				@Override
				public void run(){
					
					jedisCluster.lpush("testkey","哈哈"+at.incrementAndGet());
				}
			});
		}
		
		
	}
}
