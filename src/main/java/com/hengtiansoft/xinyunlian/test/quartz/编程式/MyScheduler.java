package com.hengtiansoft.xinyunlian.test.quartz.编程式;

import java.text.ParseException;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.BaseCalendar;
import org.quartz.impl.calendar.CronCalendar;



/**
 * 工程需要引入4个包：commons-collections-3.2.jar  commons-logging-1.0.4.jar  jta.jar  quartz-all-1.6.0.jar
 * 另外持久化还需引入commons-dbcp-1.2.1.jar commons-pool.jar log4j.1.2.15 slf4j-api-1.6.0.jar slf4j-log4j12-1.6.0.jar
 * @author Administrator
 *
 */
public class MyScheduler {

	/**调度器**/
	public static Scheduler myscheduler;
	
	public static BaseCalendar calendar;
	
	public static String calendarName="myCalendar";
	/**
	 * 初始化调度器
	 * @throws Exception
	 */
	public static void initScheduler() throws Exception{
		/**实例化调度器工厂**/
		SchedulerFactory ft = new StdSchedulerFactory();
		/**工厂中获得Scheduler实例**/
		myscheduler=ft.getScheduler();
	}
	
	
	public static void initScheduler(SchedulerFactory ft)throws Exception{
		/**工厂中获得Scheduler实例**/
		myscheduler=ft.getScheduler();
	}
	
	/**
	 * 增加日历，日历的作用是屏蔽某个时间段的触发
	 * @throws ParseException 
	 * @throws SchedulerException 
	 */
	public static void addCalender() throws Exception{
		//表达式CronCalendar
		calendar = new CronCalendar("20 * * * * ?");
		myscheduler.addCalendar(calendarName, calendar, true, true);
		
		
/*		//AnnualCalendar可以做到每年的某个时间停止触发
		AnnualCalendar cal = new AnnualCalendar(); 
		//下面的Calendar是java.util.Calendar
		Calendar gCal = Calendar.getInstance();
		gCal.set(Calendar.MONTH, Calendar.MAY);   
        gCal.set(Calendar.DATE, 10); 
        //设置AnnualCalendar
        cal.setDayExcluded(gCal, true);
        //加载到Scheduler,注意，calendar加载到Scheduler后，trigger要add这个Calendar，否则Calendar无效
        myscheduler.addCalendar("myCalendar", cal, true, true);                */
		
		//DailyCalendar一天的某个时间不触发
/*			Calendar cal1 = Calendar.getInstance();
		cal1.set(Calendar.HOUR, 3);
		cal1.set(Calendar.MINUTE, 7);
		Calendar cal2 = Calendar.getInstance();
		cal2.set(Calendar.HOUR, 3);
		cal2.set(Calendar.MINUTE, 14);
		DailyCalendar cal = new DailyCalendar(cal1,cal2);//开始时间，结束时间
		myscheduler.addCalendar("myCalendar", cal, true, true);           */
		
/*			//WeeklyCalendar一个星期中某天不触发
		WeeklyCalendar cal = new WeeklyCalendar();
		cal.setDayExcluded(6, true);//周五停止触发
		cal.setDayExcluded(5, true);//周四不触发
		myscheduler.addCalendar("myCalendar", cal, true, true);         */
        
		//MonthlyCalendar一个月中某几天不触发
	}
	
	public static void deleteGroup(String groupname){
		try{
			String[] jobnames = myscheduler.getJobNames(groupname);
			for(String jobname : jobnames){
				myscheduler.deleteJob(jobname, groupname);
			}
		}catch(Exception e){
		}	
	}
}
