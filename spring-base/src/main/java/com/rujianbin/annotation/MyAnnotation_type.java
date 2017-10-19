package com.rujianbin.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)  
@Retention(RetentionPolicy.RUNTIME) //RetentionPolicy.SOURCE  RetentionPolicy.CLASS  RetentionPolicy.RUNTIME
@Documented
public @interface MyAnnotation_type {
	String value();
}
