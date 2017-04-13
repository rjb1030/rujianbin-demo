package com.rujianbin.provider.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by 汝建斌 on 2017/4/12.
 * 验证码异常
 */
public class KaptchaException extends AuthenticationException {

    public KaptchaException(String msg, Throwable t) {
        super(msg, t);
    }

    public KaptchaException(String msg) {
        super(msg);
    }
}
