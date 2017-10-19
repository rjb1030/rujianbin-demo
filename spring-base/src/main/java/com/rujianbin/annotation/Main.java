package com.rujianbin.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class Main {

	public static void main(String[] args) throws Exception{
		//读取类注解
		Class clazz = Class.forName("com.rujianbin.annotation.AnnotationTEST");
		if(clazz.isAnnotationPresent(MyAnnotation_type.class)){
			System.out.println("AnnotationTEST类有注解");
		}
		MyAnnotation_type ann = (MyAnnotation_type)clazz.getAnnotation(MyAnnotation_type.class);
		System.out.println(ann.value());
		
		//去读方法注解
		Method method = clazz.getMethod("print");
		if(method.isAnnotationPresent(MyAnnotation_method.class)){
			System.out.println("print方法有注解");
			MyAnnotation_method ann2 = (MyAnnotation_method)method.getAnnotation(MyAnnotation_method.class);
			//获取方法所有注解
			Annotation[] anns = method.getAnnotations();
			if(anns!=null && anns.length>0){
				for(Annotation a : anns){
					System.out.println("annotationType==="+a.annotationType());
					if(a.annotationType()==MyAnnotation_method.class){
						System.out.println("注解类型匹配");
					}else{
						System.out.println("注解类型不匹配");
					}
				}
			}
		}
	}
}
