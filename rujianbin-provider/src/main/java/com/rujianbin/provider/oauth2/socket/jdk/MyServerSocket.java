package com.rujianbin.provider.oauth2.socket.jdk;

import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by 汝建斌 on 2017/4/14.
 */
public class MyServerSocket {

    public static void main(String[] args) {
        try {

            ServerSocket server = null;
            try {
                server = new ServerSocket(4700);//创建一个ServerSocket在端口4700监听客户请求
            } catch (Exception e) {
                System.out.println("can not listen to socket:" + e);
            }

            //要想被多个客户端连接，则accept拿到socket后要重新accept
            while(true) {
                Socket socket = null;

                try {

                    socket = server.accept();//使用accept()阻塞等待客户请求，有客户//请求到来则产生一个Socket对象，并继续执行
                    System.out.println("有客户端连接"+socket.getPort()+"     当前线程---->"+Thread.currentThread().getName());
                    MyServerSocketThread runner = new MyServerSocketThread(socket);
                    new Thread(runner).start();
                } catch (Exception e) {

                    System.out.println("Error." + e);

                }

            }

//            server.close(); //关闭ServerSocket
        } catch (Exception e) {
            System.out.println("Error:" + e);//出错，打印出错信息
        }
    }



    public static class MyServerSocketThread implements Runnable{

        public Socket socket;

        public MyServerSocketThread(Socket socket){
             this.socket=socket;
        }

        @Override
        public void run() {
            try{
                String line;

                final BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));//由Socket对象得到输入流，并构造相应的BufferedReader对象

                PrintWriter os = new PrintWriter(socket.getOutputStream());//由Socket对象得到输出流，并构造PrintWriter对象

                BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));//由系统标准输入设备构造BufferedReader对象

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(true){
                            try {
                                String content = is.readLine();   //readLine堵塞
                                if(!StringUtils.isEmpty(content)){
                                    System.out.println(" from Client:" + is.readLine()+"     当前线程---->"+Thread.currentThread().getName());//从Client读入一字符串，并打印到标准输出上
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();

                line = sin.readLine();//从标准输入读入一字符串。如果一直未输入，将一直堵塞

                while (!line.equals("bye")) {//如果该字符串为 "bye"，则停止循环

                    os.println(line);//向客户端输出该字符串
                    os.flush();//刷新输出流，使Client马上收到该字符串

                    line = sin.readLine();//从系统标准输入读入一字符串，

                }

                os.close(); //关闭Socket输出流

                is.close(); //关闭Socket输入流

                socket.close(); //关闭Socket
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
