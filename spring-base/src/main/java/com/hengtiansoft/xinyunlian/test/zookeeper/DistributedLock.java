package com.hengtiansoft.xinyunlian.test.zookeeper;

import java.util.Collections;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException.NoNodeException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.data.Stat;

public class DistributedLock {

	ZooKeeperClient client = new ZooKeeperClient();
	IDoSomething dosomething;
	
	private String groupNode = "d-lock";
    private String subNode = "lock";
    // 当前client创建的子节点
    private String thisPath;
    // 当前client等待的子节点
    private String waitPath;
	
    public DistributedLock(IDoSomething dosomething) throws Exception{
    	this.dosomething=dosomething;
    	client.connect();
    }
    
    /**
     * 释放锁
     * @throws Exception
     * 2016年12月14日
     * author rujianbin
     */
    private void releaseLock() {
    	String thisNode = thisPath.substring(("/" + groupNode + "/").length());
    	try {
			client.delete(thisPath);
		} catch (NoNodeException e) {
			System.out.println("当前节点"+thisNode+",删除当前节点异常，节点不存在");
		} catch (Exception e) {
			System.out.println("当前节点"+thisNode+",删除当前节点异常，系统异常");
		}
    }
    
    /**
     * 获取锁时执行业务逻辑
     * @throws Exception
     * 2016年12月14日
     * author rujianbin
     */
    private void dosomething()throws Exception{
    	String thisNode = thisPath.substring(("/" + groupNode + "/").length());
    	dosomething.dosomething(thisNode);
    	releaseLock();
    }
    
    /**
     * 是否是最小节点，即是否可以获得锁
     * @return
     * @throws Exception
     * 2016年12月14日
     * author rujianbin
     */
    private boolean isMinNode()throws Exception{
    	// 注意, 没有必要监听"/locks"的子节点的变化情况
        List<String> childrenNodes = client.getChild("/" + groupNode);
        String thisNode = thisPath.substring(("/" + groupNode + "/").length());
        if (childrenNodes.size() == 1){
        	return true;
        }else{
        	// 排序
            Collections.sort(childrenNodes);
            int index = childrenNodes.indexOf(thisNode);
            if (index == -1){
            	return true;
            }else if(index == 0){
            	return true;
            }else{
            	this.waitPath = "/" + groupNode + "/" + childrenNodes.get(index - 1);
            	return false;
            }
        }
    }
    
    /**
     * 获取编号最大节点
     * @return
     * @throws Exception
     * 2016年12月14日
     * author rujianbin
     */
    private void setMaxWaitPath()throws Exception{
    	List<String> childrenNodes = client.getChild("/" + groupNode);
    	Collections.sort(childrenNodes);
    	this.waitPath="/" + groupNode + "/" + childrenNodes.get(childrenNodes.size()-1);
    }
    
    /**
     * 当前非最小节点时 增加监听器
     * @param isWatchMaxPath  false:监听前一个节点   ;true:监听最大的节点，而不是前一个
     * @throws Exception
     * 2016年12月14日
     * author rujianbin
     */
    private void addWatcher()throws Exception{
    	// 在waitPath上注册监听器, 当waitPath被删除时, zookeeper会回调监听器的process方法
        //watch参数用于指定是否监听path node的删除事件, 以及数据更新事件, 注意, 不监听path node的创建事件, 因为如果path node不存在, 该方法将抛出KeeperException.NoNodeException异常.
        //stat参数是个传出参数, getData方法会将path node的状态信息设置到该参数中.
        
        //待优化：如果这里getData时  waitPath已经正好删除，则会抛出异常。这里需要优化。
    	final String thisNode = thisPath.substring(("/" + groupNode + "/").length());
  
    	final String waitNode = waitPath.substring(("/" + groupNode + "/").length());
    	try {
			client.getData(waitPath, new Watcher() {
			    public void process(WatchedEvent event) {
			        try {

			            // 发生了waitPath的删除事件
			            if (event.getType() == EventType.NodeDeleted && event.getPath().equals(waitPath)) {
			            	//如果目录是不同服务器的不同线程创建的，因每个线程都监听自己前面的目录，如果某台服务器挂了，则对应创建的目录也会自动被删除。将导致监听其目录的线程会启动。则导致并发。
			                //优化方法：在监听触发是 重新判断下是不是我是最小目录，不是的话 重新监听前面一个目录
			            	if(isMinNode()){
			                	dosomething();
			                }else{
			                	System.out.println("当前节点"+thisNode+",监听节点"+waitNode+",监听节点被删除，触发watch-当前非最小-重新监听");
			                	addWatcher();
			                }
			            }
			        } catch (Exception e) {
			            e.printStackTrace();
			        }
			    }
			} , new Stat());
		} catch (NoNodeException e) {
			if(isMinNode()){
				System.out.println("当前节点"+thisNode+",监听节点"+waitNode+",监听节点不存在-当前最小-执行业务");
            	dosomething();
            }else{
            	System.out.println("当前节点"+thisNode+",监听节点"+waitNode+",监听节点不存在-当前非最小-重新监听");
            	addWatcher();
            }
		}
    }
    
    /**
     * 获取分布式锁
     * @return
     * 2016年12月14日
     * author rujianbin
     */
    public void tryGetLock() throws Exception{
    	//Ids是指定权限信息。如果不指定则放开OPEN_ACL_UNSAFE
        thisPath = client.create("/" + groupNode + "/" + subNode, null,CreateMode.EPHEMERAL_SEQUENTIAL);
        if(isMinNode()){
        	dosomething();
        }else{
        	addWatcher();
        }
////     测试节点异常
//       String thisNode = thisPath.substring(("/" + groupNode + "/").length());
//       double n = Math.random();
//       if(n>0.7){
//    	   Thread.sleep(23000);
//    	   System.out.println("当前节点"+thisNode+" connection断开");
//    	   client.close();
//       }
    }
    
}
