package com.hengtiansoft.xinyunlian.test.quartz.编程式;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

//继承StatefulJob或@DisallowConcurrentExecution则该job不会同时被并发执行
//@PersistJobDataAfterExecution
//此标记说明在执行完Job的execution方法后保存JobDataMap当中固定数据,在默认情况下 也就是没有设置 @PersistJobDataAfterExecution的时候 每个job都拥有独立JobDataMap
//否则改任务在重复执行的时候具有相同的JobDataMap
public class JobImpl implements Job{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(fmt.format(new Date())+" "+context.getJobDetail().getName()+"======Job实现类在打印");
		System.out.println(fmt.format(new Date())+" "+context.getTrigger().getName()+"=======trigger的名称");
		/**获取JobDataMap中的数据**/
		JobDetail jobdetail = context.getJobDetail();
		JobDataMap jobdatamap = jobdetail.getJobDataMap();
		System.out.println(fmt.format(new Date())+" jobDetail的JobDataMap数据="+jobdatamap.get("data1"));	
		try {
			System.out.println("开始睡眠");
			Thread.sleep(50000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
