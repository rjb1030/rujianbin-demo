package com.rujianbin.provider.nio.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by 汝建斌 on 2017/4/25.
 */
public class AioServer {
    private static int threadPoolSize=10;
    private AsynchronousChannelGroup asynchronousChannelGroup;
    private AsynchronousServerSocketChannel serverSocketChannel;

    private AsynchronousSocketChannel socketChannel;

    public void setSocketChannel(AsynchronousSocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    private MyAcceptCompletionHandler myAcceptCompletionHandler;
    private MyWriteCompletionHandler myWriteCompletionHandler;
    private MyReadCompletionHandler myReadCompletionHandler;

    public AioServer(){
        try {
            myAcceptCompletionHandler = new MyAcceptCompletionHandler();
            myWriteCompletionHandler = new MyWriteCompletionHandler();
            myReadCompletionHandler = new MyReadCompletionHandler();
            //创建group AsynchronousChannelGroup绑定一个线程池，这个线程池执行两个任务：处理IO事件和派发CompletionHandler
            this.asynchronousChannelGroup = AsynchronousChannelGroup
                    .withCachedThreadPool(Executors.newCachedThreadPool(), this.threadPoolSize);
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
        System.out.println("开始接收客户端请求。。。");
        if (this.serverSocketChannel.isOpen()) {
            //accept方法的第一个参数是你想传给CompletionHandler的attchment，第二个参数就是注册的用于回调的CompletionHandler，最后返回结果Future<AsynchronousSocketChannel>
            this.serverSocketChannel.accept(this,myAcceptCompletionHandler);
        } else {
            throw new IllegalStateException("serverSocketChannel has been closed");
        }
    }


    //
    private class MyAcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, AioServer> {

        final ByteBuffer buffer = ByteBuffer.allocate(1024);

        @Override
        public void completed(AsynchronousSocketChannel socketChannel, AioServer attachment) {
            try {
                System.out.println("Accept connection from " + socketChannel.getRemoteAddress());
                attachment.setSocketChannel(socketChannel);
                socketChannel.read(buffer,buffer,myReadCompletionHandler);
            } catch (Exception e) {
                e.printStackTrace();
            }finally{
                System.out.println("继续准备接收客户端请求");
                serverSocketChannel.accept(null,myAcceptCompletionHandler);
//                try {
//                    socketChannel.close();
//                }catch (IOException e) {
//                    e.printStackTrace();
//                }
            }
        }

        @Override
        public void failed(Throwable exc, AioServer attachment) {
            System.out.println("接收请求失败 " + exc.getMessage());
            exc.printStackTrace();
        }
    }


    private class MyReadCompletionHandler implements CompletionHandler<Integer,ByteBuffer>{

        final ByteBuffer buffer = ByteBuffer.allocate(1024);

        @Override
        public void completed(Integer result, ByteBuffer attachment) {

            String msg =  new String(attachment.array());
            System.out.println("接收到客户端数据："+msg);
            String msg2 = msg.split("&")[0]+"& from server "+System.currentTimeMillis();

            buffer.clear();
            buffer.put(msg2.getBytes());
            buffer.flip();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            socketChannel.write(buffer,null,myWriteCompletionHandler);
        }

        @Override
        public void failed(Throwable exc, ByteBuffer attachment) {
            System.out.println("读数据失败");
            exc.printStackTrace();
        }
    }


    private class MyWriteCompletionHandler implements CompletionHandler<Integer,Object>{

        final ByteBuffer buffer = ByteBuffer.allocate(1024);

        @Override
        public void completed(Integer result, Object attachment) {
            System.out.println("向客户端发送字节 size="+result);
            //第一个buffer是读取通道中的内容。第二个是传给MyReadHandle complete方法的attachment对应参数
            //因异步处理，当处理完后buffer会被写入数据，扔给handle处理
            buffer.clear();
            socketChannel.read(buffer,buffer,myReadCompletionHandler);
//            System.out.println("接收到客户端数据："+new String(buffer.array()));//此处是没有数据打印的，因read是异步处理的
        }

        @Override
        public void failed(Throwable exc, Object attachment) {
            System.out.println("写数据失败");
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
