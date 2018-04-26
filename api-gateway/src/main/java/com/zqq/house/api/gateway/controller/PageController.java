package com.zqq.house.api.gateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created By 张庆庆
 * DATA: 2018/4/17
 * TIME: 15:21
 */
@Controller
public class PageController {

    @RequestMapping("")
    public String index(){
        return "/homepage/index";
    }

    @RequestMapping("index")
    public String index1(){
        return "/homepage/index";
    }
}
