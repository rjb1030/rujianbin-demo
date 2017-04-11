package com.rujianbin.provider.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 汝建斌 on 2017/4/11.
 */
public class MyLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private String defaultFailureUrl;

    public MyLoginFailureHandler(String defaultTargetUrl){
        this.defaultFailureUrl = defaultTargetUrl;
        super.setDefaultFailureUrl(defaultTargetUrl);
    }

    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if(this.defaultFailureUrl == null) {
            this.logger.debug("No failure URL set, sending 401 Unauthorized error");
            response.sendError(401, "Authentication Failed: " + exception.getMessage());
        } else {
            super.saveException(request, exception);
            String toUrl = this.defaultFailureUrl;
            if(exception instanceof UsernameNotFoundException){
                System.out.println("登录失败：用户名不存在");
                toUrl+="UsernameNotFoundException";
            }else if(exception instanceof BadCredentialsException){
                System.out.println("登录失败：密码错误");
                toUrl+="BadCredentialsException";
            }
            if(isUseForward()) {
                request.getRequestDispatcher(toUrl).forward(request, response);
            } else {
                getRedirectStrategy().sendRedirect(request, response, toUrl);
            }
        }

    }
}
