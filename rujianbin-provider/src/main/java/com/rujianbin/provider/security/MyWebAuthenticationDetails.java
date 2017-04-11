package com.rujianbin.provider.security;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 汝建斌 on 2017/4/11.
 */
public class MyWebAuthenticationDetails extends WebAuthenticationDetails {

    private String Captcha;

    public MyWebAuthenticationDetails(HttpServletRequest request){
        super(request);
        Captcha = request.getParameter("vCode");
    }

    public String getCaptcha() {
        return Captcha;
    }

    public void setCaptcha(String captcha) {
        Captcha = captcha;
    }
}
