package com.rujianbin.quartz.编程式;

import org.quartz.JobDetail;
import org.quartz.Trigger;
/**
 * 注意将classpath下的quartz.properties 备份，否则默认读取该配置文件时，任务将会持久化到数据库
 * @author Administrator
 *
 */
public class Main {

	public static void main(String[] args) throws Exception {
		MyScheduler.initScheduler();
		MyScheduler.addCalender();//20秒时阻止定时触发
		putJobToScheduler();
	}
	
	/**
	 * 添加job任务到调度器，并执行
	 */
	public static void putJobToScheduler()throws Exception{
		JobDetail jobDetail1 = MyJobDetail.getJobDetailInstance("jobdetail1_编程式","jobGroup1_编程式");
		Trigger trigger1 = MyTrigger.getTriggerInstance("triggerName1_编程式","triggerGroup1_编程式","0/2 * * * * ?");
		MyScheduler.myscheduler.scheduleJob(jobDetail1, trigger1);
		
		JobDetail jobDetail2 = MyJobDetail.getJobDetailInstance("jobdetail2_编程式","jobGroup2_编程式");
		Trigger trigger2 = MyTrigger.getTriggerInstance("triggerName2_编程式","triggerGroup2_编程式","0/5 * * * * ?");
		MyScheduler.myscheduler.scheduleJob(jobDetail2, trigger2);
		
		/**启动调度器**/
		MyScheduler.myscheduler.start();
	}
}
