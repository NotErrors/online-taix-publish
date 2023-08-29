package com.yuren.apipassenger.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/8/20-22:33
 **/
@RestController
public class TextController {

    @GetMapping("/test")
    public String test() {
        return "text api passenger";
    }
}
