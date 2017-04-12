package com.rujianbin.provider.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by 汝建斌 on 2017/4/12.
 */
public class KaptchaException extends AuthenticationException {

    public KaptchaException(String msg, Throwable t) {
        super(msg, t);
    }

    public KaptchaException(String msg) {
        super(msg);
    }
}
