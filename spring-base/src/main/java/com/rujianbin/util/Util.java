package com.rujianbin.util;

public class Util {

	public static String getClassPath(){
		return Thread.currentThread().getContextClassLoader().getResource("").getPath();
	}
	
	public static String getWEBINFPath(){
		String classpath =  Thread.currentThread().getContextClassLoader().getResource("").getPath();
		if(classpath.startsWith("/")){
			classpath = classpath.substring(1, classpath.length());
		}
		classpath=classpath.replace('/', '\\'); // 将/换成\  
		classpath=classpath.replace("file:", ""); //去掉file:  
		classpath=classpath.replace("classes\\", ""); //去掉class\  
		return classpath;
	}
	
	public static void main(String[] args) {
		System.out.println(Util.getWEBINFPath());
		System.out.println(Util.getClassPath());
	}
}
