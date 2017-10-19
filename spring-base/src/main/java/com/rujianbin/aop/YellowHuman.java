package com.rujianbin.aop;

import java.text.SimpleDateFormat;
import java.util.Date;

public class YellowHuman implements IHuman{

	@Override
	public String cry(String str) {
		System.out.println("我在cry...param="+str);
		return "cry返回值";
	}

	@Override
	public String talk(String str) {
		System.out.println("我在talk...param="+str);
		return "talk方法返回值";
	}

	@Override
	public String think(String param) {
		System.out.println("我在think...");
		int dd = 3/0;
		return "think方法返回值";
	}

	@Override
	public String getDate(Date date, String format, String ss) {
		if(date!=null)
			return new SimpleDateFormat(format).format(date);
		return null;
	}

	@Override
	public Integer getMode(Integer i) {
		// TODO Auto-generated method stub
		return null;
	}



}
