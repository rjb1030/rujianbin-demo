package com.rujianbin.provider.nio.jdk.demo1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by 汝建斌 on 2017/4/26.
 */
public class NioServer {


    /*缓冲区大小*/
    private  int BLOCK = 4096;
    private static SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
    private Selector selector;
    private InetSocketAddress address;
    private ServerSocketChannel serverSocketChannel;
    private ServerSocket serverSocket;
    private Map<String,MyClient> clientMap =new HashMap<String,MyClient>();

    public NioServer(Selector selector, InetSocketAddress address)throws IOException {

        this.selector=selector;
        this.address=address;

        // 打开服务器套接字通道
        serverSocketChannel = ServerSocketChannel.open();
        // 服务器配置为非阻塞。socketChannel有非阻塞模式，fileChannel则没有非阻塞模式
        serverSocketChannel.configureBlocking(false);//与Selector一起使用，必须处于非阻塞模式。
        // 检索与此通道关联的服务器套接字
        serverSocket = serverSocketChannel.socket();

        // 进行服务的绑定
        serverSocket.bind(address);
        /**
         * SelectionKey和selector，channel的关系：
         * 一个selector上可以注册多个channel(即1个selector可以管理多个通道)
         * 一个channel注册在一个selector上  产生一个SelectionKey（selector+channel 构建一个SelectionKey），channel的keys持有该引用。该SelectionKey里可以有多个就绪事件
         * SelectionKey有channel和channel的引用
         * selector有所有注册在上面的SelectionKey集合
         */
        serverSocketChannel.register(this.selector, SelectionKey.OP_ACCEPT);
        print("nio socket Server Start----:"+address.getAddress()+":"+address.getPort());
    }

    private class MyClient{
        public SocketChannel channel;
        public ByteBuffer sendBuffer;
        public ByteBuffer receivebuffer;

        public MyClient(SocketChannel channel,ByteBuffer sendBuffer,ByteBuffer receivebuffer){
            this.channel=channel;
            this.sendBuffer=sendBuffer;
            this.receivebuffer=receivebuffer;
        }
    }

    public void listen()throws IOException {
        while (true) {
            // 选择一组键，并且相应的通道已经打开
            this.selector.select();
            // 返回此选择器的已选择键集。
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                iterator.remove();
                //注意，一个SelectionKey可以同时又多个感兴趣事件。这取决于注册时，关注了几个事件。注册时可以用“位或”操作，同时关心多个事件。SelectionKey.OP_READ | SelectionKey.OP_WRITE
                //如果是多个感兴趣事件，则不能if else if了
                if(selectionKey.isAcceptable()){
                    handleAccpet(selectionKey);
                }else if(selectionKey.isReadable()){
                    handleRead(selectionKey);
                }else if(selectionKey.isWritable()){
                    handleWrite(selectionKey);
                }
            }
        }
    }

    public void print(String msg){
        System.out.println(Thread.currentThread().getName()+" "+fm.format(new Date())+" "+msg);
    }

    private void handleAccpet(SelectionKey selectionKey)throws IOException{
        // 返回为之创建此键的通道。
        ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
        // 接受到此通道套接字的连接。
        // 此方法返回的套接字通道（如果有）将处于阻塞模式。
        SocketChannel client = server.accept();
        clientMap.put(client.toString(),new MyClient(client,ByteBuffer.allocate(BLOCK),ByteBuffer.allocate(BLOCK)));
        print("port:"+address.getPort()+" 当前客户端连接数="+clientMap.size());
        // 配置为非阻塞
        client.configureBlocking(false);
        // 注册到selector，等待连接
        client.register(selector, SelectionKey.OP_READ);
    }

    private void handleRead(SelectionKey selectionKey)throws IOException{
        // 返回为之创建此键的通道。
        SocketChannel client = (SocketChannel) selectionKey.channel();
        MyClient myclient = clientMap.get(client.toString());
        //将缓冲区清空以备下次读取
        myclient.receivebuffer.clear();
        //读取服务器发送来的数据到缓冲区中
        int count = client.read(myclient.receivebuffer);
        if (count > 0) {
            String receiveText = new String( myclient.receivebuffer.array()).trim();
            print("服务器端接受客户端数据--:"+receiveText);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myclient.channel.register(selector, SelectionKey.OP_WRITE);
        }else{
            print("服务器端接受客户端数据--:无数据");
        }
    }

    private void handleWrite(SelectionKey selectionKey)throws IOException{

        // 返回为之创建此键的通道。
        SocketChannel client = (SocketChannel) selectionKey.channel();
        MyClient myclient = clientMap.get(client.toString());
        String sendText="hello  i am server with port " + address.getPort()+" @"+fm.format(new Date());
        //向缓冲区中输入数据
        myclient.sendBuffer.clear();
        myclient.sendBuffer.put(sendText.getBytes());
        //将缓冲区各标志复位,因为向里面put了数据标志被改变要想从中读取数据发向服务器,就要复位
        myclient.sendBuffer.flip();
        //输出到通道
        myclient.channel.write(myclient.sendBuffer);
//        print("服务器端向客户端发送数据--："+sendText);
        myclient.channel.register(selector, SelectionKey.OP_READ);
    }

    public static void main(String[] args) {
        AtomicInteger aa = new AtomicInteger(0);
        for(int i=1;i<=2;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        new NioServer(Selector.open(),new InetSocketAddress("localhost",Integer.valueOf("811"+aa.incrementAndGet())))
                                .listen();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }

    }
}
