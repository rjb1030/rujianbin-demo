package com.rujianbin.provider.security;

import com.rujianbin.provider.common.ApplicationContextSelf;
import com.rujianbin.provider.common.CookieKey;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 汝建斌 on 2017/4/10.
 */
public class MyLogoutSuccessHandle extends SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler {

    public MyLogoutSuccessHandle(String defaultTargetUrl) {
        super.setDefaultTargetUrl(defaultTargetUrl);
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        String user_key = CookieKey.getCookie(httpServletRequest,CookieKey.cookie_user_key);
        if(user_key!=null){
            System.out.println("登出成功，清除redis个人登录信息,cookieKey="+user_key);
            RedisTemplate redisTemplate = ApplicationContextSelf.getBean("redisTemplate",RedisTemplate.class);
            redisTemplate.delete(user_key);
        }
        super.onLogoutSuccess(httpServletRequest,httpServletResponse,authentication);
    }
}
