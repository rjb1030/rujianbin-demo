package com.rujianbin.provider.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created by 汝建斌 on 2017/4/11.
 */
public class MyAuthenticationProvider extends DaoAuthenticationProvider {

    public MyAuthenticationProvider(boolean hideUserNotFoundExceptions){
        super.setHideUserNotFoundExceptions(hideUserNotFoundExceptions);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException{
        return super.authenticate(authentication);
    }

    @Override
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        super.setUserDetailsService(userDetailsService);
    }

    @Override
    protected UserDetailsService getUserDetailsService() {
        return super.getUserDetailsService();
    }

}
