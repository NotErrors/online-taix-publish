package com.yuren.apipassenger.controller;

import com.yuren.apipassenger.service.TokenService;
import com.yuren.internalcommon.dto.TokenResult;
import com.yuren.internalcommon.response.ResponseResult;
import com.yuren.internalcommon.response.TokenResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/3-14:58
 **/
@RestController
@Slf4j
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @PostMapping("/refresh-token")
    public ResponseResult tokenRefresh(@RequestBody TokenResponse tokenResponse) {
        String refreshToken = tokenResponse.getRefreshToken();
        log.info("获取到的refresh-token：{}", refreshToken);

        return tokenService.refreshToken(refreshToken);
    }
}
