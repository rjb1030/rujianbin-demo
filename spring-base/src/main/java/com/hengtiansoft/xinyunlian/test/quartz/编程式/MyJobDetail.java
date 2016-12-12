package com.hengtiansoft.xinyunlian.test.quartz.编程式;

import org.quartz.JobDetail;

public class MyJobDetail {

	public static JobDetail getJobDetailInstance(String jobDetailName,String jobGroup){
		//参数1：jobdetail的name
		//参数2：jobdetail的组名
		//参数3：实现Job接口的实现类class
		JobDetail jobdetail = new JobDetail(jobDetailName,jobGroup,JobImpl.class);
		jobdetail.getJobDataMap().put("data1", "我是JobDataMap的数据");
		return jobdetail;
	}
}
