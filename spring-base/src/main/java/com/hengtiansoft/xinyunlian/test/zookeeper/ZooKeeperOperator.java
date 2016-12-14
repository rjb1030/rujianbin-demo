package com.hengtiansoft.xinyunlian.test.zookeeper;

import java.util.Arrays;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

public class ZooKeeperOperator extends AbstractZooKeeper {  
    
  
    /** 
     *  
     *<b>function:</b>创建持久态的znode,比支持多层创建.比如在创建/parent/child的情况下,无/parent.无法通过 
     *@author cuiran 
     *@createDate 2013-01-16 15:08:38 
     *@param path 
     *@param data 
     *@throws KeeperException 
     *@throws InterruptedException 
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
     *  
     *<b>function:</b>获取节点信息 
     *@author cuiran 
     *@createDate 2013-01-16 15:17:22 
     *@param path 
     *@throws KeeperException 
     *@throws InterruptedException 
     */  
    public void getChild(String path) throws Exception{     
        try{  
            List<String> list=this.zooKeeper.getChildren(path, false);  
            String[] nodes = list.toArray(new String[list.size()]); 
            Arrays.sort(nodes); 
            if(list.isEmpty()){  
            	System.out.println(path+"中没有节点");  
            }else{  
            	System.out.println(path+"中存在节点");  
                for(String child:list){  
                	System.out.println("节点为："+child);  
                }  
            }  
        }catch (Exception e) {  
            // TODO: handle exception  
             throw e;     
  
        }  
    }  
      
    public byte[] getData(String path) throws Exception {     
        return  this.zooKeeper.getData(path, false,null);     
    }    
      
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