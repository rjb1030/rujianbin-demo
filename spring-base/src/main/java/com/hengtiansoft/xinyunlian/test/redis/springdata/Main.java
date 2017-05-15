package com.hengtiansoft.xinyunlian.test.redis.springdata;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;

import com.hengtiansoft.xinyunlian.test.mq.TestBean;

/**
 * 订阅模式
 * redis单点  spring-data集成
 * @author 汝建斌
 *
 */
public class Main {

	
	
	public static void main(String[] args) throws Exception {

		//订阅模式
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		RedisTemplate redisTemplate = context.getBean("redisTemplate", RedisTemplate.class);
		redisTemplate.convertAndSend("java1", new TestBean("哈哈1",23));
		redisTemplate.convertAndSend("java2", new TestBean("哈哈2",23));
		System.out.println("已发送信息");

		//数据储存 key-value
		ValueOperations<String,TestBean> ops = redisTemplate.opsForValue();
		ops.set("rujianbin-key", new TestBean("哈哈value",56),100,TimeUnit.MILLISECONDS);//100毫秒超时
		TestBean bean = (TestBean)ops.get("rujianbin-key");
		System.out.println("缓存数据value---"+bean.getName());

		//list操作，可以用作队列
		ListOperations<String,TestBean> opsList = redisTemplate.opsForList();
		opsList.leftPush("rujianbin-list", new TestBean("哈哈list",56));
		opsList.leftPush("rujianbin-list", new TestBean("哈哈list2",57));
		TestBean beanL = opsList.rightPop("rujianbin-list");
		System.out.println("缓存数据List---"+beanL.getName());
		TestBean beanL2 = opsList.rightPop("rujianbin-list");
		System.out.println("缓存数据List---"+beanL2.getName());

		//set操作。和list的区别是随机弹出
		SetOperations<String,TestBean> opsSet = redisTemplate.opsForSet();
		opsSet.add("rujianbin-set", new TestBean("哈哈Set",57));
		opsSet.add("rujianbin-set", new TestBean("哈哈Set2",57));
		TestBean beanS = opsSet.pop("rujianbin-set"); //随机弹出一个元素，和list有序弹出不一样
		TestBean beanS2 = opsSet.pop("rujianbin-set");
		System.out.println("缓存数据Set---"+beanS.getName());
		System.out.println("缓存数据Set---"+beanS2.getName());


		ZSetOperations<String,TestBean> opsZSet = redisTemplate.opsForZSet();
		opsZSet.add("rujianbin-zset", new TestBean("哈哈zSet1",57),1);
		opsZSet.add("rujianbin-zset", new TestBean("哈哈zSet2",57),2);
		opsZSet.add("rujianbin-zset", new TestBean("哈哈zSet3",57),3);
		opsZSet.add("rujianbin-zset", new TestBean("哈哈zSet4",57),4);
		Set<TestBean> beanSet = opsZSet.range("rujianbin-zset", 0, 0); //取zset集合n到m个，包含边界
		Set<TestBean> beanSet2 = opsZSet.rangeByScore("rujianbin-zset", 2, 3); //取指定score范围的元素
		for(TestBean b : beanSet){
			System.out.println("缓存数据ZSet---"+b.getName());
		}
		for(TestBean b : beanSet2){
			System.out.println("缓存数据ZSet-------"+b.getName());
		}
		//hashMap操作
		HashOperations<String, String, TestBean> opsHash = redisTemplate.opsForHash();
		opsHash.put("rujianbin-hash", "哈哈hash1",new TestBean("哈哈hash1",57));
		opsHash.put("rujianbin-hash", "哈哈hash2",new TestBean("哈哈hash2",57));
		TestBean beanH = opsHash.get("rujianbin-hash", "哈哈hash1");
		TestBean beanH2 = opsHash.get("rujianbin-hash", "哈哈hash1");
		System.out.println("缓存数据Hash---"+beanH.getName());
		System.out.println("缓存数据Hash---"+beanH2.getName());

		System.exit(1);
	}
}
