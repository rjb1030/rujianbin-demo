package com.rujianbin.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

//实现反射接口InvocationHandler
public class handlerImpl implements InvocationHandler{

	private Object obj;
	//某个被代理类的对象即接口的实现类对象，接下来调用该对象的方法都是通过反射来实现
	public handlerImpl(Object obj){
		this.obj=obj;
	}
	//反射的实现，调用方法前后可以做想要的处理
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)throws Throwable {
		System.out.println("================调用方法前(这里可以做一些方法执行前的处理，如验证)=======================");
		Object result = method.invoke(obj, args);
		System.out.println("=================调用方法后(这里可以做一些方法执行后的处理，如回滚动作)======================");
		return result;
	}

	//获得业务接口实现类对象obj的动态代理类对象，该代理对象可以直接使用接口的方法。
	//参数为业务接口实现类的类加载器+业务接口+反射接口的实现类对象
	//本方法可以建一个工厂，在工厂中获得代理对象，此处为了简便，直接在InvocationHandler实现类中实现了
	public static Object getProxy(Object obj){   
		return Proxy.newProxyInstance(obj.getClass().getClassLoader(), 
				obj.getClass().getInterfaces(), 
				new handlerImpl(obj));
	}
	
	
	
}
