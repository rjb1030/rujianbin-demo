package com.rujianbin.memcached;

public class Main {

	public static void main(String[] args){
		memcachedUtil.add("data2", "hello world",10000);
		System.out.println(memcachedUtil.get("data2"));
	}
}
