package com.rujianbin.quartz.集成spring;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * spring的job继承QuartzJobBean（其已经继承Job接口）   原生quartz继承Job接口
 * @author Administrator
 * applicationContext.xml直接引入配置quartz的相关bean（springQuartz.xml）,启动时即可触发
 */
public class QuartzJobImpl extends QuartzJobBean {

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(fmt.format(new Date())+" "+context.getJobDetail().getName()+"======Job实现类在打印");
		System.out.println(fmt.format(new Date())+" "+context.getTrigger().getName()+"=======trigger的名称");
		/**获取JobDataMap中的数据**/
		JobDetail jobdetail = context.getJobDetail();
		JobDataMap jobdatamap = jobdetail.getJobDataMap();
		System.out.println(fmt.format(new Date())+" jobDetail的JobDataMap数据="+jobdatamap.get("data1"));	
		
	}

}
