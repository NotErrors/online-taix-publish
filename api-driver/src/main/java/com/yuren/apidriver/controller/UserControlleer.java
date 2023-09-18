package com.yuren.apidriver.controller;

import com.yuren.apidriver.service.UserService;
import com.yuren.internalcommon.dto.DriverUser;
import com.yuren.internalcommon.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/9-10:53
 **/
@RestController
public class UserControlleer {

    @Autowired
    private UserService userService;

    @PutMapping("/user")
    public ResponseResult updateDriverUser(@RequestBody DriverUser driverUser) {
        return userService.updateDriverUser(driverUser);
    }
}
