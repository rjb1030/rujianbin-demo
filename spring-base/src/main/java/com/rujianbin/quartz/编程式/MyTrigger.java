package com.rujianbin.quartz.编程式;

import org.quartz.CronTrigger;

/**
 * "0 15 10 L * ? 2010"    2010年每月最后一日的上午10:15
 * "0/5 * * * * ?"   从0秒 每5秒执行一次
 * "0 57 20 23 * ? *"    每年每个月23日 8:57:00规则
 * @author Administrator
 *
 */
public class MyTrigger {

	public static CronTrigger getTriggerInstance(String triggerName,String triggerGroup,String cron)throws Exception{
		CronTrigger trigger = new CronTrigger(triggerName,triggerGroup);
		trigger.setCronExpression(cron);
		if(MyScheduler.calendar!=null){
			trigger.setCalendarName(MyScheduler.calendarName);
		}
		return trigger;
	}
}
