package com.maple.spider.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zhangfuzeng
 * @date 2022/7/26
 */
@Controller
public class WebController {
    @RequestMapping(value = "/md.html")
    public String commonController() {
        return "md.html";
    }

    
}
