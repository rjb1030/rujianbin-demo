package com.rujianbin;

import org.springframework.web.util.HtmlUtils;

/**
 * Created by rujianbin@xinyunlian.com on 2017/5/10.
 */
public class Main {

    public static void main(String[] args) {

            System.out.println(HtmlUtils.htmlEscape(HtmlUtils.htmlEscape("&")));

    }
}
