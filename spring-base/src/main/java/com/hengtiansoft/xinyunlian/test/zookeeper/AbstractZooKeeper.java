package com.hengtiansoft.xinyunlian.test.zookeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

public abstract class AbstractZooKeeper implements Watcher {  
  
    //缓存时间  
     private static final int SESSION_TIME   = 2000;     
     public ZooKeeper zooKeeper;  
     //CountDownLatch类是一个同步计数器,构造时传入int参数,该参数就是计数器的初始值，每调用一次countDown()方法，计数器减1,计数器大于0 时，await()方法会阻塞程序继续执行
     protected CountDownLatch countDownLatch=new CountDownLatch(1);  
  
     public void connect(String hosts) throws IOException, InterruptedException{     
            zooKeeper = new ZooKeeper(hosts,SESSION_TIME,this);     
            countDownLatch.await();
            System.out.println("连接成功");
      }     
  
    /* (non-Javadoc) 
     * @see org.apache.zookeeper.Watcher#process(org.apache.zookeeper.WatchedEvent) 
     */  
    @Override  
    public void process(WatchedEvent event) {  
        // TODO Auto-generated method stub  
    	System.out.println("哈哈哈  watch被触发");
        if(event.getState()==KeeperState.SyncConnected){  
            countDownLatch.countDown();  
        }  
    }  
      
    public void close() throws InterruptedException{     
        zooKeeper.close();     
    }

	public ZooKeeper getZooKeeper() {
		return zooKeeper;
	}  
    
    
}  
