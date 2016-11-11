package com.hengtiansoft.xinyunlian.test.redis.jedisSingle;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hengtiansoft.xinyunlian.test.mq.TestBean;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class Main {

	public static void main(String[] args) {
		//ShardedJedisPool配置的集群，其实是客户端集群。即服务端的redis还是非集群模式，客户端通过哈希一致性算法，决定将数据存储到哪个redis服务端
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		ShardedJedisPool shardedJedisPool = context.getBean("shardedJedisPool",ShardedJedisPool.class);
		ShardedJedis shardedJedis = shardedJedisPool.getResource();
		shardedJedis.set("rujianbin-jedis-single".getBytes(), SerializeUtil.serialize(new TestBean("哈哈jedis-single",23)));
		byte[] rsObj = shardedJedis.get("rujianbin-jedis-single".getBytes());
		TestBean bean = (TestBean)SerializeUtil.unserialize(rsObj);
		System.out.println("缓存数据jedis-single------"+bean.getName());
		shardedJedis.close();
		
		System.exit(1);
	}
}
