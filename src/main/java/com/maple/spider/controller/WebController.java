package com.maple.spider.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zhangfuzeng
 * @date 2022/7/26
 */
@Controller
public class WebController {

    @RequestMapping(value = "/{module}/{name}")
    public String commonController2(@PathVariable String module, @PathVariable String name) {
        return module + "/" + name;
    }

    @RequestMapping(value = "/{name}")
    public String commonController(@PathVariable String name) {
        return name;
    }
}
