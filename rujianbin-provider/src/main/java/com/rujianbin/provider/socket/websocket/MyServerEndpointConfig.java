package com.rujianbin.provider.socket.websocket;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 * Created by 汝建斌 on 2017/4/18.
 * 如果httpSession是保存在在服务器上的  则可以获取。如果是保存在redis里，则因为socket连接时并没有传cookie,所以无法获取httpSession
 */
public class MyServerEndpointConfig extends ServerEndpointConfig.Configurator {

//    @Override
//    public void modifyHandshake(ServerEndpointConfig config,
//                                HandshakeRequest request,
//                                HandshakeResponse response) {
//        HttpSession httpSession = (HttpSession)request.getHttpSession();
//        config.getUserProperties().put(HttpSession.class.getName(),httpSession);
//    }
}
