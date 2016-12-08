package com.hengtiansoft.xinyunlian.test.quartz.自带定时;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;



/**
 * 引入springScheduler.xml文件到applicationContext.xml  启动tamcat
 * @author Administrator
 *
 */
@Component
public class MyAnnotationJob {

	@Scheduled(cron="0/5 * *  * * ? ")
	public void scheduler(){
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(fmt.format(new Date())+" "+"我是spring注解触发的定时任务");
	}
}
