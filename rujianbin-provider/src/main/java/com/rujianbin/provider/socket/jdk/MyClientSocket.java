package com.rujianbin.provider.socket.jdk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by 汝建斌 on 2017/4/14.
 *
 * socket的服务端和客户端都是在java端的
 */
public class MyClientSocket {

    public static void main(String[] args) {
        try{

            //因本线程被 while(!readline.equals("bye"))循环阻塞    故发起多个client连接，可以多次执行main函数
            Socket socket=new Socket("127.0.0.1",4700);//向本机的4700端口发出客户请求
            BufferedReader sin=new BufferedReader(new InputStreamReader(System.in));//由系统标准输入设备构造BufferedReader对象
            PrintWriter os=new PrintWriter(socket.getOutputStream());//由Socket对象得到输出流，并构造PrintWriter对象
            final BufferedReader is=new BufferedReader(new InputStreamReader(socket.getInputStream()));//由Socket对象得到输入流，并构造相应的BufferedReader对象
            String readline;

            new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true){
                        try {
                            System.out.println(" from Server:"+is.readLine()+"     当前线程---->"+Thread.currentThread().getName());//从Server读入一字符串，并打印到标准输出上
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();

            readline=sin.readLine();    //从系统标准输入读入一字符串。如果一直未输入，将一直堵塞
            while(!readline.equals("bye")){     //若从标准输入读入的字符串为 "bye"则停止循环
                os.println(readline);//将从系统标准输入读入的字符串输出到Server
                os.flush();//刷新输出流，使Server马上收到该字符串

                readline=sin.readLine(); //从系统标准输入读入一字符串  如果一直未输入，将一直堵塞
            }

            os.close(); //关闭Socket输出流

            is.close(); //关闭Socket输入流

            socket.close(); //关闭Socket


        }catch(Exception e) {
                System.out.println("Error"+e); //出错，则打印出错信息
        }
    }

}
