package com.hengtiansoft.xinyunlian.test.zookeeper;

import org.apache.zookeeper.data.Stat;

public class Main {

    public static void main(String[] args) {  
        try {     
               ZooKeeperOperator zkoperator             = new ZooKeeperOperator();     
               zkoperator.connect("192.168.91.228:2181,192.168.91.228:2182,192.168.91.228:2183");  
                 
               byte[] data = new byte[]{'a','b','c','d'};     
                    
//               zkoperator.create("/root",null);     
//               System.out.println(Arrays.toString(zkoperator.getData("/root")));
                    
               String zktest="ZooKeeper的Java API测试";  
               String mypath  = zkoperator.create("/root/aachild_", zktest.getBytes());  
               System.out.println("我创建的路径"+mypath);
               System.out.println("获取设置的信息："+new String(zkoperator.getData(mypath)));  
                 
               System.out.println("节点孩子信息:");     
               zkoperator.getChild("/root");    
               
               Stat stat = zkoperator.exists("/root/aachild_0000000005");
               if(stat!=null){
            	   zkoperator.getZooKeeper().wait();
               }   
               zkoperator.close();     
                 
               //如果要实现分布式锁，将创建的路径和取回的所有节点的路径做对比。   最小的是我创建的  即获得锁。否则监控比自己创建的index小的目录，
                 
           } catch (Exception e) {     
               e.printStackTrace();     
           }     
 
   } 
}
