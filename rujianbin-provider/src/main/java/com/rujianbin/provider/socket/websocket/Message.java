package com.rujianbin.provider.socket.websocket;

import java.io.Serializable;

/**
 * Created by 汝建斌 on 2017/4/18.
 */
public class Message implements Serializable{

    private static final long serialVersionUID = -1626424369503425841L;
    private String from;
    private String content;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
