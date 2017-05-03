package com.rujianbin.provider.oauth2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by rujianbin@xinyunlian.com on 2017/5/3.
 */

@Controller
@RequestMapping("/oauth2")
public class Oauth2Controller {

    @RequestMapping("/hello")
    @ResponseBody
    public String login(HttpServletRequest request, HttpServletResponse response) {
        return "hello world! you are authorization";
    }
}
