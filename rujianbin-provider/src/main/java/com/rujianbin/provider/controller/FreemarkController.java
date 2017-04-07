package com.rujianbin.provider.controller;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/providerFMK")
public class FreemarkController {

	//127.0.0.1:7070/rujianbin-provider/providerFMK/index
	@RequestMapping("/index")
    public String welcome(ModelMap model) {
        model.put("time", new Date());
        model.put("message", "hello freemark");
        return "index";
    }

    @RequestMapping("/login")
    public String login(ModelMap model) {
        return "login";
    }
	
}
