package com.yuren.apidriver.controller;

import com.yuren.apidriver.service.VerificationCodeService;
import com.yuren.internalcommon.request.VerificationCodeDTO;
import com.yuren.internalcommon.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/9-17:02
 **/
@RestController
@Slf4j
public class VerificationCodeController {

    @Autowired
    private VerificationCodeService verificationCodeService;

    @GetMapping("/verification-code")
    public ResponseResult verificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO) {
        log.info("获取到的司机手机号：{}", verificationCodeDTO.getDriverPhone());
        return verificationCodeService.checkAndSendVerificationCode(verificationCodeDTO.getDriverPhone());
    }

    @PostMapping("/verification-code-check")
    public ResponseResult checkVerificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO) {

        String driverPhone = verificationCodeDTO.getDriverPhone();
        String verificationCode = verificationCodeDTO.getVerificationCode();
        log.info("获取到的手机号：{}，获取到的验证码：{}",driverPhone,verificationCode);

        return verificationCodeService.checkVerificationCode(driverPhone, verificationCode);
    }
}
