package com.hengtiansoft.xinyunlian.test.quartz.持久化;

import org.quartz.JobDetail;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import com.hengtiansoft.xinyunlian.test.quartz.编程式.MyJobDetail;
import com.hengtiansoft.xinyunlian.test.quartz.编程式.MyScheduler;
import com.hengtiansoft.xinyunlian.test.quartz.编程式.MyTrigger;

/**
 * 工程需要引入4个包：commons-collections-3.2.jar  commons-logging-1.0.4.jar  jta.jar  quartz-all-1.6.0.jar
 * 另外持久化还需引入commons-dbcp-1.2.1.jar commons-pool.jar log4j.1.2.15 slf4j-api-1.6.0.jar slf4j-log4j12-1.6.0.jar
 * 持久化基本和编程式一样，只是读取的不再是默认配置文件，而是自定义quartz.properties
 * 建议用1.8.6版本的建立数据库脚本实现持久化
 *  
 * 重命名quartz.properties.持久化配置--->quartz.properties
 * @author Administrator
 *
 */
public class Main {

	public static void main(String[] args) throws Exception {
		SchedulerFactory ft = new StdSchedulerFactory("quartz/quartz.持久化配置.properties");
		MyScheduler.initScheduler(ft);
		MyScheduler.addCalender();//20秒时阻止定时触发
		putJobToScheduler();
	}
	
	
	/**
	 * 添加job任务到调度器，并执行
	 */
	public static void putJobToScheduler()throws Exception{
		JobDetail jobDetail1 = MyJobDetail.getJobDetailInstance("jobdetail1_持久化式","jobGroup1_持久化式");
		Trigger trigger1 = MyTrigger.getTriggerInstance("triggerName1_持久化式","triggerGroup1_持久化式","0/2 * * * * ?");
		MyScheduler.myscheduler.scheduleJob(jobDetail1, trigger1);
		
		JobDetail jobDetail2 = MyJobDetail.getJobDetailInstance("jobdetail2_持久化式","jobGroup2_持久化式");
		Trigger trigger2 = MyTrigger.getTriggerInstance("triggerName2_持久化式","triggerGroup2_持久化式","0/5 * * * * ?");
		MyScheduler.myscheduler.scheduleJob(jobDetail2, trigger2);
		
		/**启动调度器**/
		MyScheduler.myscheduler.start();
	}
}
