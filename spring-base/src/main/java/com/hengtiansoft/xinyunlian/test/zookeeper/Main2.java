package com.hengtiansoft.xinyunlian.test.zookeeper;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class Main2 {
	
	/**
	 * 注意, 创建ZooKeeper对象时, 只要对象完成初始化便立刻返回. 建立连接是以异步的形式进行的, 当连接成功建立后, 会回调watcher的process方法. 如果想要同步建立与server的连接, 需要自己进一步封装.
	 * getChildren, getData, exists方法可指定是否监听相应的事件. 而create, delete, setData方法则会触发相应的事件的发生.
	 * znode节点中关联的数据不能超过1M. zookeeper的使命是分布式协作, 而不是数据存储.
	 * 相关的连接操作， 也可以使用ZKClient客户端jar包
	 */
    // 超时时间
    private static final int SESSION_TIMEOUT = 5000;
    // zookeeper server列表
    private String hosts = "192.168.91.228:2181,192.168.91.228:2182,192.168.91.228:2183";  //可以配置多个IP，逗号隔开，zookeeper尝试连接第一个，连不上尝试连接第二个
    private String groupNode = "root";
    private String subNode = "mylock";

    private ZooKeeper zk;
    // 当前client创建的子节点
    private String thisPath;
    // 当前client等待的子节点
    private String waitPath;

    private CountDownLatch latch = new CountDownLatch(1);

    /**
     * 连接zookeeper
     */
    public void connectZookeeper() throws Exception {
        zk = new ZooKeeper(hosts, SESSION_TIMEOUT, new Watcher() {
            public void process(WatchedEvent event) {
                try {
                    // 连接建立时, 打开latch, 唤醒wait在该latch上的线程
                    if (event.getState() == KeeperState.SyncConnected) {
                    	System.out.println("zookeeper连接成功");
                        latch.countDown();
                    }

                    // 发生了waitPath的删除事件
                    if (event.getType() == EventType.NodeDeleted && event.getPath().equals(waitPath)) {
                        System.out.println("waitPath节点被删除，触发watcher");
                    	doSomething();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // 等待连接建立
        latch.await();

        // 创建子节点
        //Ids是指定权限信息。如果不指定则放开OPEN_ACL_UNSAFE
        thisPath = zk.create("/" + groupNode + "/" + subNode, null, Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL_SEQUENTIAL);

        // wait一小会, 让结果更清晰一些
        Thread.sleep(10);

        // 注意, 没有必要监听"/locks"的子节点的变化情况
        List<String> childrenNodes = zk.getChildren("/" + groupNode, false);

        // 列表中只有一个子节点, 那肯定就是thisPath, 说明client获得锁
        if (childrenNodes.size() == 1) {
            doSomething();
        } else {
            String thisNode = thisPath.substring(("/" + groupNode + "/").length());
            // 排序
            Collections.sort(childrenNodes);
            int index = childrenNodes.indexOf(thisNode);
            if (index == -1) {
                // never happened
            } else if (index == 0) {
                // inddx == 0, 说明thisNode在列表中最小, 当前client获得锁
                doSomething();
            } else {
                // 获得排名比thisPath前1位的节点
            	System.out.println("不是最小的");
                this.waitPath = "/" + groupNode + "/" + childrenNodes.get(index - 1);
                // 在waitPath上注册监听器, 当waitPath被删除时, zookeeper会回调监听器的process方法
                //watch参数用于指定是否监听path node的删除事件, 以及数据更新事件, 注意, 不监听path node的创建事件, 因为如果path node不存在, 该方法将抛出KeeperException.NoNodeException异常.
                //stat参数是个传出参数, getData方法会将path node的状态信息设置到该参数中.
                //待优化：如果这里getData时  waitPath已经正好删除，则会抛出异常。这里需要优化。同时，如果目录是不同服务器的不同线程创建的，因每个线程都监听自己前面的目录，如果某台服务器挂了，则对应创建的目录也会自动被删除。将导致监听其目录的线程会启动。则导致并发。
                //优化方法：在监听触发是 重新判断下是不是我是最小目录，不是的话 重新监听前面一个目录
                zk.getData(waitPath, true, new Stat());
            }
        }
    }

    private void doSomething() throws Exception {
        try {
            System.out.println("gain lock: " + thisPath);
            Thread.sleep(2000);
            // do something
        } finally {
            System.out.println("finished: " + thisPath);
            // 将thisPath删除, 监听thisPath的client将获得通知
            // 相当于释放锁
            zk.delete(this.thisPath, -1);
        }
    }

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 10; i++) {
            new Thread() {
                public void run() {
                    try {
                        Main2 dl = new Main2();
                        dl.connectZookeeper();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }

        Thread.sleep(Long.MAX_VALUE);
    }
}
