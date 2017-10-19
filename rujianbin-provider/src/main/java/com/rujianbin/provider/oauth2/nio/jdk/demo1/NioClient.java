package com.rujianbin.provider.oauth2.nio.jdk.demo1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by 汝建斌 on 2017/4/26.
 */
public class NioClient {

    private static SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");

    private String name;

    private InetSocketAddress address;
    /*缓冲区大小*/
    private static int BLOCK = 4096;
    /*接受数据缓冲区*/
    private ByteBuffer sendbuffer = ByteBuffer.allocate(BLOCK);
    /*发送数据缓冲区*/
    private ByteBuffer receivebuffer = ByteBuffer.allocate(BLOCK);

    private SocketChannel socketChannel;
    private Selector selector;

    public NioClient(Selector selector, InetSocketAddress address, String name) throws IOException {
        this.address=address;
        this.selector=selector;
        this.name=name;

        // 打开socket通道
        socketChannel = SocketChannel.open();
        // 设置为非阻塞方式
        socketChannel.configureBlocking(false);

        // 注册连接服务端socket动作
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
        // 连接
        socketChannel.connect(address);
    }

    public void print(String msg){
        System.out.println(Thread.currentThread().getName()+" "+fm.format(new Date())+" "+name+" "+msg);
    }

    public void listen()throws IOException{
        Set<SelectionKey> selectionKeys;
        Iterator<SelectionKey> iterator;
        SelectionKey selectionKey;

        while (true) {
            //选择一组键，其相应的通道已为 I/O 操作准备就绪。
            //此方法执行处于阻塞模式的选择操作。
            selector.select();
            //返回此选择器的已选择键集。
            selectionKeys = selector.selectedKeys();
            iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                selectionKey = iterator.next();
                if (selectionKey.isConnectable()) {
                    handleConnect(selectionKey);
                } else if (selectionKey.isReadable()) {
                    handleRead(selectionKey);
                } else if (selectionKey.isWritable()) {
                    handleWrite(selectionKey);
                }
            }
            selectionKeys.clear();
        }
    }

    private void handleConnect(SelectionKey selectionKey)throws IOException{
        print("client connectting...");
        socketChannel = (SocketChannel) selectionKey.channel();
        // 判断此通道上是否正在进行连接操作。
        // 完成套接字通道的连接过程。
        if (socketChannel.isConnectionPending()) {
            //非阻塞模式finishConnect判断是否连接成功。阻塞模式下finishConnect方法也会阻塞，直到连接成功
            while (!socketChannel.finishConnect()){
                print("还是连接中..........");
            }
            print("完成连接!");
            sendbuffer.clear();
            sendbuffer.put(("Hello,i am client "+name+" @"+fm.format(new Date())).getBytes());
            sendbuffer.flip();
            socketChannel.write(sendbuffer);
        }
        socketChannel.register(selector, SelectionKey.OP_READ);
    }
    private void handleRead(SelectionKey selectionKey)throws IOException{
        socketChannel = (SocketChannel) selectionKey.channel();
        //将缓冲区清空以备下次读取
        receivebuffer.clear();
        //读取服务器发送来的数据到缓冲区中
        int count=socketChannel.read(receivebuffer);
        if(count>0){
            String receiveText = new String( receivebuffer.array()).trim();
            print("客户端接受服务器端数据--:"+receiveText);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            socketChannel.register(selector, SelectionKey.OP_WRITE);
        }else{
            print("客户端接受服务器端数据--:无数据");
        }
    }

    private void handleWrite(SelectionKey selectionKey)throws IOException{
        sendbuffer.clear();
        socketChannel = (SocketChannel) selectionKey.channel();
        String sendText = "Hello,i am client "+name+" @"+fm.format(new Date());
        sendbuffer.put(sendText.getBytes());
        //将缓冲区各标志复位,因为向里面put了数据标志被改变要想从中读取数据发向服务器,就要复位
        sendbuffer.flip();
        socketChannel.write(sendbuffer);
//        print("客户端向服务器端发送数据--："+sendText);
        socketChannel.register(selector, SelectionKey.OP_READ);
    }


    public static void main(String[] args) throws IOException{

        //正常场景是 一个Selector可以注册多个Channel 即可以做到多路复用。此处为方便 一个selector一个channel
        AtomicInteger aa = new AtomicInteger(0);
        for(int i=1;i<=1000;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        new NioClient(Selector.open(),new InetSocketAddress("localhost",Integer.valueOf("8111")),"张三"+aa.incrementAndGet())
                                .listen();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }

        AtomicInteger bb = new AtomicInteger(0);
        for(int i=1;i<=15;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        new NioClient(Selector.open(),new InetSocketAddress("localhost",Integer.valueOf("8112")),"李四"+bb.incrementAndGet())
                                .listen();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
