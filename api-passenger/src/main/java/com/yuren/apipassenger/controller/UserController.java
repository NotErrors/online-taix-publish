package com.yuren.apipassenger.controller;

import com.yuren.apipassenger.service.UserService;
import com.yuren.internalcommon.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/3-16:39
 **/
@RestController
@Slf4j
public class UserController {


    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public ResponseResult getUser(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization");


        return userService.getUserInfoByToken(accessToken);
    }
}
