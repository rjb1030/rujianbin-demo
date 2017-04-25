package com.rujianbin.provider.nio.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * Created by 汝建斌 on 2017/4/25.
 */
public class AioServer {
    private static int threadPoolSize=10;
    private AsynchronousChannelGroup asynchronousChannelGroup;
    private AsynchronousServerSocketChannel serverSocketChannel;

    private List<AioConnectionedClient> clientList = new ArrayList<AioConnectionedClient>();

    public static class AioConnectionedClient{
        public AsynchronousSocketChannel socketChannel;
        public MyAcceptCompletionHandler myAcceptCompletionHandler;
        public MyWriteCompletionHandler myWriteCompletionHandler;
        public MyReadCompletionHandler myReadCompletionHandler;
        public ByteBuffer buffer;
    }

    public AioServer(){
        try {

            //创建group AsynchronousChannelGroup绑定一个线程池，这个线程池执行两个任务：处理IO事件和派发CompletionHandler
            this.asynchronousChannelGroup = AsynchronousChannelGroup
                    .withThreadPool(Executors.newFixedThreadPool(this.threadPoolSize));
            //AsynchronousServerSocketChannel可以绑定一个 AsynchronousChannelGroup，那么通过这个AsynchronousServerSocketChannel建立的连接都将同属于一个AsynchronousChannelGroup并共享资源
            this.serverSocketChannel = AsynchronousServerSocketChannel
                    .open(this.asynchronousChannelGroup);
            //通过nio 2.0引入的SocketOption类设置一些TCP选项
            this.serverSocketChannel
                    .setOption(
                            StandardSocketOptions.SO_REUSEADDR,true);
            this.serverSocketChannel
                    .setOption(
                            StandardSocketOptions.SO_RCVBUF,16*1024);
            this.serverSocketChannel
                    .bind(new InetSocketAddress("localhost",8111), 100); //100等待连接的队列大小，默认50个

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pendingAccept() {
        print("开始接收客户端请求。。。");
        if (this.serverSocketChannel.isOpen()) {
            AioConnectionedClient client = new AioConnectionedClient();
            client.myAcceptCompletionHandler = new MyAcceptCompletionHandler();
            client.myWriteCompletionHandler = new MyWriteCompletionHandler();
            client.myReadCompletionHandler = new MyReadCompletionHandler();
            clientList.add(client);
            //accept方法的第一个参数是你想传给CompletionHandler的attchment，第二个参数就是注册的用于回调的CompletionHandler，最后返回结果Future<AsynchronousSocketChannel>
            this.serverSocketChannel.accept(client,client.myAcceptCompletionHandler);
        } else {
            throw new IllegalStateException("serverSocketChannel has been closed");
        }
    }

    public void print(String msg){
        System.out.println(Thread.currentThread().getName()+" "+msg);
    }

    //
    private class MyAcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, AioConnectionedClient> {

        final ByteBuffer buffer = ByteBuffer.allocate(1024);

        @Override
        public void completed(AsynchronousSocketChannel socketChannel, AioConnectionedClient client) {
            try {
                print("Accept connection from " + socketChannel.getRemoteAddress());
                print("当前客户端连接数="+clientList.size());
                client.socketChannel = socketChannel;
                client.buffer=buffer;
                socketChannel.read(buffer,client,client.myReadCompletionHandler);
            } catch (Exception e) {
                e.printStackTrace();
            }finally{
                pendingAccept();
//                try {
//                    socketChannel.close();
//                }catch (IOException e) {
//                    e.printStackTrace();
//                }
            }
        }

        @Override
        public void failed(Throwable exc, AioConnectionedClient attachment) {
            print("接收请求失败 " + exc.getMessage());
            exc.printStackTrace();
        }
    }


    private class MyReadCompletionHandler implements CompletionHandler<Integer,AioConnectionedClient>{

        final ByteBuffer buffer = ByteBuffer.allocate(1024);

        @Override
        public void completed(Integer result, AioConnectionedClient client) {

            String msg =  new String(client.buffer.array());
            print("接收到客户端数据："+msg);
            String msg2 = msg.split("&")[0]+"& from server "+System.currentTimeMillis();

            buffer.clear();
            buffer.put(msg2.getBytes());
            buffer.flip();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            client.socketChannel.write(buffer,client,client.myWriteCompletionHandler);
        }

        @Override
        public void failed(Throwable exc, AioConnectionedClient attachment) {
            print("读数据失败");
            exc.printStackTrace();
        }
    }


    private class MyWriteCompletionHandler implements CompletionHandler<Integer,AioConnectionedClient>{

        final ByteBuffer buffer = ByteBuffer.allocate(1024);

        @Override
        public void completed(Integer result, AioConnectionedClient client) {
//            print("向客户端发送字节 size="+result);
            //第一个buffer是读取通道中的内容。第二个是传给MyReadHandle complete方法的attachment对应参数
            //因异步处理，当处理完后buffer会被写入数据，扔给handle处理
            buffer.clear();
            client.buffer = buffer;
            client.socketChannel.read(buffer,client,client.myReadCompletionHandler);
//            System.out.println("接收到客户端数据："+new String(buffer.array()));//此处是没有数据打印的，因read是异步处理的
        }

        @Override
        public void failed(Throwable exc, AioConnectionedClient attachment) {
            print("写数据失败");
            exc.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new AioServer().pendingAccept();
        while(true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
