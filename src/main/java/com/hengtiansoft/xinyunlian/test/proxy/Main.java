package com.hengtiansoft.xinyunlian.test.proxy;

public class Main {

	public static void main(String[] args){
		Iinterface obj = (Iinterface)handlerImpl.getProxy(new Impl());
		obj.getString();
		obj.sayHello();
		obj.setString("hello, memo has changed!");
	}
}
