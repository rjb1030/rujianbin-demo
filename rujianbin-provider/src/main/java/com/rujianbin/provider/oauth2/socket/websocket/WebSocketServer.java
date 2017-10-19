package com.rujianbin.provider.oauth2.socket.websocket;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by 汝建斌 on 2017/4/14.
 */

@ServerEndpoint(value="/websocket",
        //configurator = MyServerEndpointConfig.class,
        encoders = MyEncoder.class)
@Component
public class WebSocketServer {

    private static int onlineCount = 0;
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();

    private Session session;
    private HttpSession httpSession;
    private String nickname;

    @OnOpen
    public void onOpen( Session session, EndpointConfig config){
//        this.httpSession = (HttpSession) config.getUserProperties()
//                .get(HttpSession.class.getName());
        this.nickname = nickname;
        this.session = session;
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
        //群发消息
        for(WebSocketServer item: webSocketSet){
            try {

                item.sendObjectMessage("系统",getName()+"加入聊天室");
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    @OnClose
    public void onClose(){
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
        //群发消息
        for(WebSocketServer item: webSocketSet){
            try {
                item.sendObjectMessage("系统",getName()+"离开聊天室");
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端("+getName()+")的消息:" + message);
        //群发消息
        for(WebSocketServer item: webSocketSet){
            try {
                if(!item.getSession().getId().equals(session.getId())){
                    item.sendObjectMessage(getName(),message);
                }
            } catch (IOException e) {
                e.printStackTrace();
                 continue;
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable error){
        System.out.println("发生错误");
        error.printStackTrace();
    }

    public void sendMessage(String message) throws IOException{
        this.session.getBasicRemote().sendText(message);
    }

    public void sendObjectMessage(String from,String message) throws IOException{
        Message msg = new Message();
        msg.setFrom(from);
        msg.setContent(message);
        try {
            this.session.getBasicRemote().sendObject(msg);
        } catch (EncodeException e) {
            e.printStackTrace();
        }
    }

    public String getName(){
//        RjbSecurityUser user = (RjbSecurityUser)this.httpSession.getAttribute("userInfo");
//        return user.getName()+session.getId();
        return nickname;
    }

    public Session getSession(){
        return this.session;
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

}
