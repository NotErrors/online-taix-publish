package com.yuren.serviceverificationcode.controller;

import com.alibaba.fastjson.JSONObject;
import com.yuren.internalcommon.dto.NumberResponse;
import com.yuren.internalcommon.response.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/8/26-21:45
 **/
@RestController
public class NumberCodeController {

    @GetMapping("/numbercode/{size}")
    public ResponseResult numbercode(@PathVariable(name = "size") Integer size) {
        // 生成验证码
        int numberCode =(int)((Math.random() * 9 + 1) * (Math.pow(10, size - 1)));

        // 封装结果
        ResponseResult success = ResponseResult.success(new NumberResponse(numberCode));
        return success;
    }
}
