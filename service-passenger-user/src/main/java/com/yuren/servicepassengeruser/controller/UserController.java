package com.yuren.servicepassengeruser.controller;

import com.yuren.internalcommon.request.VerificationCodeDTO;
import com.yuren.internalcommon.response.ResponseResult;
import com.yuren.servicepassengeruser.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/8/31-22:16
 **/
@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public ResponseResult loginOrRegister(@RequestBody VerificationCodeDTO verificationCodeDTO) {
        String passengerPhone = verificationCodeDTO.getPassengerPhone();
        log.info("获取到的手机号：{}", passengerPhone);

        userService.loginOrRegister(passengerPhone);

        return ResponseResult.success();
    }

    @GetMapping("/user/{phone}")
    public ResponseResult getUserByPhone(@PathVariable(name = "phone")String passengerPhone) {
        return userService.getUserByPhone(passengerPhone);
    }
}
