package com.rujianbin.provider.nio.netty;

import org.jboss.netty.bootstrap.ServerBootstrap;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 汝建斌 on 2017/4/27.
 */
public class HelloServer {

    public static void main(String[] args) {
        // Server服务启动器
        ExecutorService bossExecutor = Executors.newCachedThreadPool();
        ExecutorService workerExecutor = Executors.newCachedThreadPool();
        NioServerSocketChannelFactory nioServerSocketChannelFactory = new NioServerSocketChannelFactory(bossExecutor, workerExecutor);
        ServerBootstrap bootstrap = new ServerBootstrap(nioServerSocketChannelFactory);
        // 设置一个处理客户端消息和各种消息事件的类(Handler)
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                return Channels.pipeline(new HelloServerHandle());
            }
        });

        bootstrap.bind(new InetSocketAddress(8111));
    }

    public void print(String msg){
        System.out.println(Thread.currentThread().getName()+" "+msg);
    }

    private static class HelloServerHandle extends SimpleChannelHandler {

        /**
         * 当有客户端绑定到服务端的时候触发，打印"Hello world, I'm server."
         * @param ctx
         * @param e
         */
        @Override
        public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
            System.out.println("connected success.");

            // 将字符串，构造成ChannelBuffer，传递给服务端
            String msg = "Hello, I'm server.";
            ChannelBuffer buffer = ChannelBuffers.buffer(msg.length());
            buffer.writeBytes(msg.getBytes());
            e.getChannel().write(buffer);
        }

        @Override
        public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
            ChannelBuffer buffer = (ChannelBuffer) e.getMessage();
            System.out.println("来自客户端："+buffer.toString(Charset.defaultCharset()));
        }
    }
}
