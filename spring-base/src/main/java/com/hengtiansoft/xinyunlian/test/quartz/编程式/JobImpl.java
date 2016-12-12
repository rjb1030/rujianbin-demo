package com.hengtiansoft.xinyunlian.test.quartz.编程式;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

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
	}
}
