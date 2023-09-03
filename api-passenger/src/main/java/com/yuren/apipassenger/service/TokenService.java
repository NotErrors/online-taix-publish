package com.yuren.apipassenger.service;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.yuren.internalcommon.constant.CommonStatusConstant;
import com.yuren.internalcommon.constant.TokenConfigConstant;
import com.yuren.internalcommon.dto.TokenResult;
import com.yuren.internalcommon.response.ResponseResult;
import com.yuren.internalcommon.response.TokenResponse;
import com.yuren.internalcommon.util.JWTUtils;
import com.yuren.internalcommon.util.RedisPrefixUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.apache.bcel.classfile.ConstantString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/3-15:00
 **/
@Service
@Slf4j
public class TokenService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public ResponseResult refreshToken(String refreshTokenSrc) {
        log.info("解析token");
        TokenResult tokenResult = JWTUtils.parseToken(refreshTokenSrc);

        if (Objects.isNull(tokenResult)) {
            return ResponseResult.responseResult(CommonStatusConstant.TOKEN_CHECK_ERROR.getCode(), CommonStatusConstant.TOKEN_CHECK_ERROR.getMessage(), null);
        }

        log.info("获取token信息");
        String phone = tokenResult.getPhone();
        String identity = tokenResult.getIdentity();
        String refreshTokenKey = RedisPrefixUtils.generatorTokenKey(phone, identity, TokenConfigConstant.REFRESH_TOKEN_KEY);


        log.info("获取redis中存储的refreshToken，校验token");
        String token = stringRedisTemplate.opsForValue().get(refreshTokenKey);
        if (StringUtils.isBlank(token) || !token.trim().equals(refreshTokenSrc.trim())) {
            return ResponseResult.responseResult(CommonStatusConstant.TOKEN_CHECK_ERROR.getCode(), CommonStatusConstant.TOKEN_CHECK_ERROR.getMessage(), null);
        }

        log.info("生成双token");
        String accessTokenKey = RedisPrefixUtils.generatorTokenKey(phone, identity, TokenConfigConstant.ACCESS_TOKEN_KEY);
        String accessToken = JWTUtils.generatorToken(phone, identity, TokenConfigConstant.ACCESS_TOKEN_KEY);
        String refreshToken = JWTUtils.generatorToken(phone, identity, TokenConfigConstant.REFRESH_TOKEN_KEY);

        stringRedisTemplate.opsForValue().set(accessTokenKey, accessToken, 30 , TimeUnit.DAYS);
        stringRedisTemplate.opsForValue().set(refreshTokenKey, refreshToken, 31 , TimeUnit.DAYS);

        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(accessToken);
        tokenResponse.setRefreshToken(refreshToken);
        return ResponseResult.success(tokenResponse);
    }
}
