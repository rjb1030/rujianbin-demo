package com.hengtiansoft.xinyunlian.test.zookeeper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DoSomethingImpl implements IDoSomething{

	@Override
	public void dosomething(String node) {
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:MM:ss").format(new Date())+"   "+node+"-获得了锁，处理业务逻辑...");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
