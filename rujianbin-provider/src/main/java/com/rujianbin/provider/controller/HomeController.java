package com.rujianbin.provider.controller;

import com.rujianbin.provider.common.CookieKey;
import com.rujianbin.provider.security.RjbSecurityUser;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by 汝建斌 on 2017/4/10.
 */
@Controller
@RequestMapping("/home")
public class HomeController {

    @RequestMapping("")
    public String login(HttpServletRequest request, HttpServletResponse response,ModelMap model) {
        Object obj = request.getSession().getAttribute("userInfo");
        if(obj!=null){
            RjbSecurityUser rjbSecurityUser  = (RjbSecurityUser)obj;
            model.put("user",rjbSecurityUser.getName()+"("+rjbSecurityUser.getUsername()+")");
            model.put("authority",rjbSecurityUser.getAuthorities());
        }

        System.out.println("sessionId----->"+request.getSession().getId());
        return "home/home";
    }


    @RequestMapping("/hello")
    @ResponseBody
    public String login(HttpServletRequest request, HttpServletResponse response) {
        return "hello world! you are authorization";
    }


    @RequestMapping("/chat-room")
    public String chatRoom(HttpServletRequest request, HttpServletResponse response,ModelMap model) {
        return "websocket/chat-room";
    }


}
