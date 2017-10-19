package com.rujianbin.provider.oauth2.thread;

/**
 * Created by 汝建斌 on 2017/4/24.
 */
public class Main {

    private static int num = 0;

    static Thread t1 = new Thread(new Counter1());
    static Thread t2 = new Thread(new Counter2());

    public static void main(String[] args) {


        t1.start();

        t2.start();

//        for(int i=1;i<=200;i++){
//            num++;
//        }
//
//        System.out.println(Thread.currentThread().getName()+",num="+num);

//        while (true){
//
//        }


    }

    public static class Counter1 implements Runnable{
        @Override
        public void run() {
                for(int i=1;i<=200;i++){
                    num++;
                    try {
                        Thread.sleep(5);
//                        if(i==165){
//                            t2.join();    //t2突然join 则当前调用线程挂起，直到t2运行结束才继续执行
//                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName()+",num="+num);
        }
    }

    public static class Counter2 implements Runnable{
        @Override
        public void run() {
            for(int i=1;i<=200;i++){
                num++;
                try {
                    Thread.sleep(8);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName()+",num="+num);
        }
    }
}
