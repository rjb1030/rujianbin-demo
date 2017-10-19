package com.rujianbin.annotation;

@MyAnnotation_type(value="这是类的注解")
public class AnnotationTEST {

	@MyAnnotation_method(myvalue="这是方法的注解",needCache="需要缓存true")
	public void print(){
		System.out.println("测试自定义注解");
	}
	
	
}
