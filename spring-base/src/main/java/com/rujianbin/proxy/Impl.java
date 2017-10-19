package com.rujianbin.proxy;

public class Impl implements Iinterface{

	private String memo="hello world!";
	@Override
	public String getString() {
		System.out.println("================================================");
		System.out.println("调用getString()方法，打印出返回值为---->"+this.memo);
		return this.memo;
	}

	@Override
	public void sayHello() {
		System.out.println("================================================");
		System.out.println("调用sayHello()方法，打印值为--->你好");
	}

	@Override
	public void setString(String str) {
		System.out.println("================================================");
		System.out.println("没有设置memo之前--->memo为"+this.memo);
		this.memo=str;
		System.out.println("设置memo之后--->memo为"+this.memo);
	}

	
}
