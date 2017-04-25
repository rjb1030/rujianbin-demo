package com.rujianbin.provider.nio.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * Created by 汝建斌 on 2017/4/25.
 */
public class AioClient {

    private String name;

    private AsynchronousSocketChannel client;
    private InetSocketAddress serverAddress = new InetSocketAddress("localhost",8111);
    private MyConnectHandle myConnectHandle;
    private MyWriteHandler myWriteHandler;
    private MyReadHandle myReadHandle;


    public AioClient(String name){
        try {
            this.name=name;
            client = AsynchronousSocketChannel.open();
            myConnectHandle = new MyConnectHandle();
            myWriteHandler = new MyWriteHandler();
            myReadHandle = new MyReadHandle();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connect(){
        //连接需要回调handle
        client.connect(serverAddress, null, myConnectHandle);
    }

    public void print(String msg){
        System.out.println(Thread.currentThread().getName()+" "+name+" "+msg);
    }

    private class MyConnectHandle implements CompletionHandler<Void,Object>{

        @Override
        public void completed(Void result, Object attachment) {
            print("连接成功");
            //写需要回调handle
            String msg = ("hello & from client [] {} ").replace("{}",String.valueOf(System.currentTimeMillis())).replace("[]",name);
            client.write(ByteBuffer.wrap(msg.getBytes()),null,myWriteHandler);
        }

        @Override
        public void failed(Throwable exc, Object attachment) {
            exc.printStackTrace();
        }
    }

    private class MyWriteHandler implements CompletionHandler<Integer,Object>{

        private ByteBuffer buffer = ByteBuffer.allocate(1024);

        @Override
        public void completed(Integer result, Object attachment) {
//            print("向服务端发送字节 size="+result);
            //第一个buffer是读取通道中的内容。第二个是传给MyReadHandle complete方法的attachment对应参数
            //因异步处理，当处理完后buffer会被写入数据，扔给handle处理
            buffer.clear();
            client.read(buffer,buffer,myReadHandle);
//            System.out.println("接收到服务端数据："+new String(buffer.array()));//此处是没有数据打印的，因read是异步处理的
        }

        @Override
        public void failed(Throwable exc, Object attachment) {
            print("写数据失败");
            exc.printStackTrace();
        }
    }

    private class MyReadHandle implements CompletionHandler<Integer,ByteBuffer>{

        private ByteBuffer buffer = ByteBuffer.allocate(1024);

        @Override
        public void completed(Integer result, ByteBuffer attachment) {

            String msg =  new String(attachment.array());
            print("接收到服务端数据："+msg);
            String msg2 = (msg.split("&")[0]+"& from client [] "+System.currentTimeMillis()).replace("[]",name);

            buffer.clear();
            buffer.put(msg2.getBytes());
            buffer.flip();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            client.write(buffer,null,myWriteHandler);
        }

        @Override
        public void failed(Throwable exc, ByteBuffer attachment) {
            print("读数据失败");
            exc.printStackTrace();
        }
    }

    public static void main(String[] args) {

        for (int i=1;i<=10;i++) {
            new AioClient("王"+i).connect();
        }
        while(true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
