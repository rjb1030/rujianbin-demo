package com.hengtiansoft.xinyunlian.test.zookeeper;

public class Main {


	
	/**
	 * 分布式锁测试
	 * @param args
	 * @throws Exception
	 * 2016年12月14日
	 * author rujianbin
	 */
	public static void main(String[] args) throws Exception{
		for (int i = 0; i < 10; i++) {
            new Thread() {
                public void run() {
                    try {
                    	DistributedLock dlock = new DistributedLock(new DoSomethingImpl());
                    	dlock.tryGetLock();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }

        Thread.sleep(Long.MAX_VALUE);
	}
}
