package com.hengtiansoft.xinyunlian.test.executor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;

public class Main_volatile {

	
	/*
	 
	volatile关键字
	  
	  
	 有一个内存区域是jvm虚拟机栈，每一个线程运行时都有一个线程栈，

	线程栈保存了线程运行时候变量值信息。当线程访问某一个对象时候值的时候，首先通过对象的引用找到对应在堆内存的变量的值，然后把堆内存

	变量的具体值load到线程本地内存中，建立一个变量副本，之后线程就不再和对象在堆内存变量值有任何关系，而是直接修改副本变量的值，

	在修改完之后的某一个时刻（线程退出之前），自动把线程变量副本的值回写到对象在堆中变量。这样在堆中的对象的值就产生变化了。
	
	由于操作并不是原子性，也就是 在堆内存变量之后，如果堆内存count变量发生修改之后，线程工作内存中的值由于已经加载，不会产生对应的变化，所以计算出来的结果会和预期不一样
	 
	原本不加volatile变量则线程在执行完后才将变量写回堆内存（中间可能操作很多次）。而volatile修饰后，在使用变量前都强迫从共享内存中重读该成员变量的值。而且，当成员变量发生变化时，强迫线程将变化值回写到堆内存。这样在任何时刻，两个不同的线程总是看到某个成员变量的同一个值。但依然可能并发
	 
	 */
	
	public static volatile int count = 0;   //加不加volatile都可能得到非1000的结果
	 
    public static void inc() {
 
        //这里延迟1毫秒，使得结果明显
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
        }
 
        count++;
    }
    
    public static void main(String[] args) {
    	 
        //同时启动1000个线程，去进行i++计算，看看实际结果
 
        for (int i = 0; i < 1000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                	Main_volatile.inc();
                }
            }).start();
        }
 
        //这里每次运行的值都有可能不同,可能为1000
        System.out.println("运行结果:Counter.count=" + Main_volatile.count);
   
        

    }
}
