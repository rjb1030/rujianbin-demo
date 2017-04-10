package com.rujianbin.provider.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by 汝建斌 on 2017/4/10.
 */

@Controller
@RequestMapping("/common")
public class CommonController {

    @RequestMapping("/403")
    public String _403(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        return "403";
    }

    @RequestMapping("/404")
    public String _404(HttpServletRequest request, HttpServletResponse response,ModelMap model) {
        return "404";
    }

    @RequestMapping("/500")
    public String _500(HttpServletRequest request, HttpServletResponse response,ModelMap model) {
        return "500";
    }
}
