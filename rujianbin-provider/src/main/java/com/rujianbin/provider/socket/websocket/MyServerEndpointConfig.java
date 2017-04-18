package com.rujianbin.provider.socket.websocket;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 * Created by 汝建斌 on 2017/4/18.
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
