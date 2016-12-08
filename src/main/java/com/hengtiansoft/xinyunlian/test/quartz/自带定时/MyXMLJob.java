package com.hengtiansoft.xinyunlian.test.quartz.自带定时;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 引入springScheduler.xml文件到applicationContext.xml   启动tamcat
 * @author Administrator
 *
 */
public class MyXMLJob {

	public void print(){
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(fmt.format(new Date())+" "+"我是spring自带的xml配置的定时任务  print");
	}
	
	public void show(){
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(fmt.format(new Date())+" "+"我是spring自带的xml配置的定时任务  show");
	}
}
