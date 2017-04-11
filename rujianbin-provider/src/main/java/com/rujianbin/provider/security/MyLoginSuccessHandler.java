package com.rujianbin.provider.security;

import com.rujianbin.provider.common.ApplicationContextSelf;
import com.rujianbin.provider.common.CookieKey;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by 汝建斌 on 2017/4/1.
 */
public class MyLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {


    public MyLoginSuccessHandler(String defaultTargetUrl){
        super.setDefaultTargetUrl(defaultTargetUrl);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication) throws IOException,
            ServletException {
        //获得授权后可得到用户信息   可使用SUserService进行数据库操作
        RjbSecurityUser rjbSecurityUser = (RjbSecurityUser)authentication.getPrincipal();
       /* Set<SysRole> roles = userDetails.getSysRoles();*/
        //输出登录提示信息
        System.out.println("用户 " + rjbSecurityUser.getName() + "，username="+rjbSecurityUser.getUsername()+" 登录");

        System.out.println("IP :"+getIpAddress(request));

        //个人登录信息放入redis
        String uuid = UUID.randomUUID().toString();
        String cookieKeyStr = CookieKey.cookie_prefix+uuid;
        RedisTemplate redisTemplate = ApplicationContextSelf.getBean("redisTemplate",RedisTemplate.class);
        redisTemplate.opsForValue().set(cookieKeyStr,rjbSecurityUser);
        redisTemplate.expire(cookieKeyStr,30, TimeUnit.MINUTES);
        Cookie cookie = new Cookie(CookieKey.cookie_user_key,cookieKeyStr);
        response.addCookie(cookie);

        System.out.println("cookieKeyStr ----->"+cookieKeyStr);
        System.out.println("successHandle sessionId----->"+request.getSession().getId());
        super.onAuthenticationSuccess(request, response, authentication);
    }

    public String getIpAddress(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
