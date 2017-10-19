package com.rujianbin.zookeeper;

import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

public class ZooKeeperClient extends AbstractZooKeeper {  
    
	
  
    /**
     * 创建节点
     * @param path
     * @param data
     * @return
     * @throws Exception
     * 2016年12月14日
     * author rujianbin
     */
    public String create(String path,byte[] data)throws Exception{  
        /** 
         * 此处采用的是CreateMode是PERSISTENT  表示The znode will not be automatically deleted upon client's disconnect. 
         * EPHEMERAL 表示The znode will be deleted upon the client's disconnect. 
         * _SEQUENTIAL代表自动编号
         */   
        return this.zooKeeper.create(path, data, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);  
    }  
    
    /**
     * 创建节点
     * @param path
     * @param data
     * @param mode
     * @return
     * @throws Exception
     * 2016年12月14日
     * author rujianbin
     */
    public String create(String path,byte[] data,CreateMode mode)throws Exception{  
        /** 
         * 此处采用的是CreateMode是PERSISTENT  表示The znode will not be automatically deleted upon client's disconnect. 
         * EPHEMERAL 表示The znode will be deleted upon the client's disconnect. 
         * _SEQUENTIAL代表自动编号
         */   
        return this.zooKeeper.create(path, data, Ids.OPEN_ACL_UNSAFE, mode);  
    }  
    /**
     * 获取子节点
     * @param path
     * @return
     * @throws Exception
     * 2016年12月14日
     * author rujianbin
     */
    public List<String> getChild(String path) throws Exception{     
        List<String> list=this.zooKeeper.getChildren(path, false);  
        return list;
    }  
     /**
      * 获取节点内容 
      * @param path
      * @return
      * @throws Exception
      * 2016年12月14日
      * author rujianbin
      */
    public byte[] getData(String path) throws Exception {     
        return  this.zooKeeper.getData(path, false,null);     
    }
    
    public byte[] getData(String path,boolean isWatch) throws Exception {     
        return  this.zooKeeper.getData(path, isWatch,null);     
    }
    
    public byte[] getData(String path,Watcher watcher,Stat stat) throws Exception {     
        return  this.zooKeeper.getData(path, watcher,stat);     
    }
    
    public void delete(String path) throws Exception{
    	this.zooKeeper.delete(path,-1);
    }
    /**
     * 节点是否存在
     * @param path
     * @return
     * 2016年12月14日
     * author rujianbin
     */
    public Stat exists(String path){
    	try {
			return this.zooKeeper.exists(path,true);
			
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }
    
    
}  