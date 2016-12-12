package com.hengtiansoft.xinyunlian.test.quartz.配置文件式;

import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.hengtiansoft.xinyunlian.test.quartz.编程式.MyScheduler;

/**
 * 重命名quartz.properties.xml配置--->quartz.properties
 * @author Administrator
 *
 */
public class Main {

	public static void main(String[] args) throws Exception {
		SchedulerFactory ft = new StdSchedulerFactory("quartz/quartz.xml配置.properties");
		Scheduler sch = ft.getScheduler();
		sch.start();
	}
	
	/**
	 * 添加job任务到调度器，并执行
	 */
	public static void putJobToScheduler()throws Exception{
		/**启动调度器**/
		MyScheduler.myscheduler.start();
	}
}
