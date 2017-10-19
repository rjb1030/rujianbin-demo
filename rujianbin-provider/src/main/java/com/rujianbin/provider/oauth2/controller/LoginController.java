package com.rujianbin.provider.oauth2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by 汝建斌 on 2017/4/10.
 */

@Controller
@RequestMapping("/login")
public class LoginController {

    @RequestMapping("")
    public String login(ModelMap model) {
        return "login/login";
    }

    @RequestMapping(value = "/logout",method = {RequestMethod.POST,RequestMethod.GET})
    public String logout(ModelMap model) {
        System.out.println("成功退出。。。。");
        return "403";
    }
}
