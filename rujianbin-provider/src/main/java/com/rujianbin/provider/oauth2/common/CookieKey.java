package com.rujianbin.provider.oauth2.common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by 汝建斌 on 2017/4/10.
 */
public class CookieKey {

    public static final String cookie_user_key="ck_u";

    public static String getCookie(HttpServletRequest request,String cookieKey){
        Cookie[] cookies = request.getCookies();
        if(cookies!=null && cookies.length>0){
            for(Cookie c : cookies){
                if(cookieKey.equals(c.getName())){
                    return c.getValue();
                }
            }
        }
        return null;
    }
}
