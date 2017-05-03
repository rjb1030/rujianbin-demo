package com.rujianbin.provider.nio.netty;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 汝建斌 on 2017/4/27.
 */
public class HelloClient {

    public static void main(String[] args) {
        // Client服务启动器
        ExecutorService bossExecutor = Executors.newCachedThreadPool();
        ExecutorService workerExecutor = Executors.newCachedThreadPool();
        NioClientSocketChannelFactory nioClientSocketChannelFactory = new NioClientSocketChannelFactory(bossExecutor, workerExecutor);
        ClientBootstrap bootstrap = new ClientBootstrap(nioClientSocketChannelFactory);
        // 设置一个处理服务端消息和各种消息事件的类(Handler)
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                return Channels.pipeline(new HelloClientHandler());
            }
        });
        // 连接到本地的8000端口的服务端
        bootstrap.connect(new InetSocketAddress("127.0.0.1", 8111));
    }

    private static class HelloClientHandler extends SimpleChannelHandler {
        @Override
        public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
            System.out.println("connected success.");

            // 将字符串，构造成ChannelBuffer，传递给服务端
            String msg = "Hello, I'm client.";
            ChannelBuffer buffer = ChannelBuffers.buffer(msg.length());
            buffer.writeBytes(msg.getBytes());
            e.getChannel().write(buffer);
        }

        @Override
        public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
            ChannelBuffer buffer = (ChannelBuffer) e.getMessage();
            System.out.println("来自服务端："+buffer.toString(Charset.defaultCharset()));
        }
    }
}
