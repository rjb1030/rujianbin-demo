package com.rujianbin.provider.controller;

import com.rujianbin.provider.common.CookieKey;
import com.rujianbin.provider.security.RjbSecurityUser;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by 汝建斌 on 2017/4/10.
 */
@Controller
@RequestMapping("/home")
public class HomeController {

    @Resource(name="redisTemplate")
    private RedisTemplate redisTemplate;

    @RequestMapping("")
    public String login(HttpServletRequest request, HttpServletResponse response,ModelMap model) {
        String user_key = CookieKey.getCookie(request,CookieKey.cookie_user_key);
        if(user_key!=null){
            RjbSecurityUser rjbSecurityUser  = (RjbSecurityUser)redisTemplate.opsForValue().get(user_key);
            model.put("user",rjbSecurityUser.getName()+"("+rjbSecurityUser.getUsername()+")");
            model.put("authority",rjbSecurityUser.getAuthorities());
        }

        return "home/home";
    }


}
