package com.rujianbin.memcached;

import java.util.Calendar;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

public class memcachedUtil {
	
	private static MemCachedClient client = new MemCachedClient();
	
	static{
		//获取连接池实例
		SockIOPool pool = SockIOPool.getInstance();
		//服务器列表及其权重
		String[] server_ip = {"127.0.0.1:11211","127.0.0.1:11311"};
		Integer[] weight = {1};
		//设置服务器信息
		pool.setServers(server_ip);
		pool.setWeights(weight);
		//设置初始连接数，最小连接数，最大连接数，最大处理时间
		pool.setInitConn(5);
		pool.setMinConn(5);
		pool.setMaxConn(10);
		pool.setMaxIdle(1000*60);
		//设置连接池守护线程的睡眠时间
		pool.setMaintSleep(60);
		//设置TCP参数，连接超时
		pool.setNagle(false);
		pool.setSocketTO(60);
		pool.setSocketConnectTO(0);
		//初始化并启动连接池
		pool.initialize();
		
		//压缩设置，超过指定大小的都压缩
		client.setCompressEnable(true);
		client.setCompressThreshold(1024*1024);	
	}
	
	public static void add(String key,String value){
		client.add(key, value);
	}
	
	public static void add(String key,String value,Integer millisecond){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MILLISECOND, millisecond);
		client.add(key, value, cal.getTime());
	}

	public static Object get(String key){
		return client.get(key);
	}
	
	public static void delete(String key){
		client.delete(key);
	}

	
	
}
