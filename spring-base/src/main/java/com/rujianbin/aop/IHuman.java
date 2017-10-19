package com.rujianbin.aop;

import java.util.Date;

public interface IHuman {

	public String cry(String str);
	
	public String talk(String str);
	
	public String think(String ss);
	
	public String getDate(Date date,String format,String ss);
	
	public Integer getMode(Integer i);
}
