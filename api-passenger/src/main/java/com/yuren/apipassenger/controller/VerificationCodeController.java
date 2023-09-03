package com.yuren.apipassenger.controller;

import com.yuren.internalcommon.request.VerificationCodeDTO;
import com.yuren.apipassenger.service.VerificationCodeService;
import com.yuren.internalcommon.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/8/20-22:56
 **/

@Slf4j
@RestController
public class VerificationCodeController {

    @Autowired
    private VerificationCodeService verificationCodeService;

    /**
     *  @RequestParam    @RequestBody的区别
     *
     *
     * @param verificationCodeDTO
     * @return
     */
    @GetMapping("/verification-code")
    public ResponseResult verificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO) {
        String passengerPhone = verificationCodeDTO.getPassengerPhone();
        return ResponseResult.success(verificationCodeService.verificationCode(passengerPhone));
    }

    @PostMapping("/verification-code-check/")
    public ResponseResult checkVerificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO) {

        String passengerPhone = verificationCodeDTO.getPassengerPhone();
        String verificationCode = verificationCodeDTO.getVerificationCode();
        log.info("获取到的手机号：{}，获取到的验证码：{}",passengerPhone,verificationCode);

        return verificationCodeService.checkVerificationCode(passengerPhone, verificationCode);
    }
}
