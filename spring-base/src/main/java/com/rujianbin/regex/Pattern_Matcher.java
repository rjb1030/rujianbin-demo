package com.rujianbin.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Pattern_Matcher {

	public static void main(String[] args){
		/*****************正则表达式切割  类似于String的split*****************/
		Pattern p = Pattern.compile("[/]+"); //将正则表达式编译并赋予给Pattern类
		String str = "ni  hao./大海 哦sdf、/凯文加 内特/good moning";
		String[] arr = p.split(str);//p.split(str,2)切割成2组
		for(int i=0;i<arr.length;i++){
			System.out.println(arr[i]);
		}
		
		/***************Matcher使用*******************/
		String expr = "*n2-3 5,45.5~55.7";
		Pattern p2 = Pattern.compile("^(\\*?)(d|td|e|n|s|c)?(\\d*)-?(\\d*)\\s*(\\d*),?([^~]*)~?(.*)$"); //^~表示不包含“~”
		Matcher m = p2.matcher(expr);
		m.find();//尝试在目标字符串里查找下一个匹配子串
		System.out.println(m.lookingAt());//检查目标字符串是否以匹配的子串起始
		System.out.println(m.matches());//只有整个字符串完全匹配时才true
		for(int i=1;i<=m.groupCount();i++){
			System.out.println(m.group(i));//m.group()返回当前查找而获得的与组匹配的所有子串内容
		}
		System.out.println("=======================================");
		Pattern p3 = Pattern.compile("Kelvin"); 
		Matcher m3 = p3.matcher("Kelvin Li and Kelvin Chan are both working in Kelvin Chen's KelvinSoftShop company");
		StringBuffer sb = new StringBuffer();
	     int j=0;
	     boolean result = m3.find(); 
	     while(result){
	    	 j++; 
	    	 m3.appendReplacement(sb, "Jack"); //前匹配子串替换为指定字符串，并且将替换后的子串以及其之前到上次匹配子串之后的字符串段添加到一个StringBuffer对象里
	    	 System.out.println("第"+j+"次匹配后sb的内容是："+sb.toString()); 
	    	 result = m3.find(); 
	     }
	   //最后调用appendTail()方法将最后一次匹配后的剩余字符串加到sb里； 
	     m3.appendTail(sb); 
	     System.out.println("调用m3.appendTail(sb)后sb的最终内容是:"+ sb.toString()); 
	     } 
	}

