package com.yuren.apidriver.service;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.yuren.apidriver.client.ServiceDriverUserClient;
import com.yuren.apidriver.client.ServiceVerificationCodeClient;
import com.yuren.internalcommon.constant.*;
import com.yuren.internalcommon.dto.NumberResponse;
import com.yuren.internalcommon.request.VerificationCodeDTO;
import com.yuren.internalcommon.response.DriverUserCheckResponse;
import com.yuren.internalcommon.response.ResponseResult;
import com.yuren.internalcommon.response.TokenResponse;
import com.yuren.internalcommon.util.JWTUtils;
import com.yuren.internalcommon.util.RedisPrefixUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/9-17:04
 **/

@Service
@Slf4j
public class VerificationCodeService {

    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    @Autowired
    private ServiceVerificationCodeClient serviceVerificationCodeClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public ResponseResult checkAndSendVerificationCode(String driverPhone) {
        log.info("判断司机是否存在");
        ResponseResult<DriverUserCheckResponse> driverUserCheckResponseResponseResult = serviceDriverUserClient.checkDriver(driverPhone);
        DriverUserCheckResponse driverUserCheckResponse = driverUserCheckResponseResponseResult.getData();
        if (driverUserCheckResponse.getIfExists() == DriverCarConstants.DRIVER_NOT_EXISTS) {
            return ResponseResult.fail(CommonStatusConstant.DRIVER_NOT_EXISTS.getCode(), CommonStatusConstant.DRIVER_NOT_EXISTS.getMessage());
        }
        log.info("获取验证码");
        ResponseResult<NumberResponse> numbercode = serviceVerificationCodeClient.numbercode(CommonConstant.PASSENGERLENGTH);
        Integer code = numbercode.getData().getNumbercode();
        log.info("验证码：{}" , code);
        
        log.info("通过第三方api发送短信");

        log.info("redis存储验证码");
        String redisDriverKey = RedisPrefixUtils.generatorKeyByPhone(driverPhone, IdentityConstant.IDENTITY_DRIVER);
        log.info("生成的key：{}", redisDriverKey);
        stringRedisTemplate.opsForValue().set(redisDriverKey, code.toString(), 2 , TimeUnit.MINUTES);

        return ResponseResult.success();
    }

    /**
     * 校验验证码
     * @param driverPhone 手机号
     * @param verificationCode 验证码
     * @return
     */
    public ResponseResult checkVerificationCode(String driverPhone, String verificationCode) {
        String key = RedisPrefixUtils.generatorKeyByPhone(driverPhone, IdentityConstant.IDENTITY_DRIVER);
        // todo 此处要增加一个删除逻辑，避免并发操作，可以采用lua脚本进行执行
        log.info("生成的key：{}", key);
        String code = stringRedisTemplate.opsForValue().get(key);
        log.info("获取验证码：{}", code);

        if (StringUtils.isBlank(code)) {
            log.info("验证码过期或者不存在");
            return ResponseResult.responseResult(CommonStatusConstant.VERIFICATION_CODE_EXPIRE.getCode(),CommonStatusConstant.VERIFICATION_CODE_EXPIRE.getMessage(),null);
        }

        log.info("校验验证码：redis：{}，接收到的：{}", code, verificationCode);
        if (!code.trim().equals(verificationCode.trim())) {
            log.info("验证码错误");
            return ResponseResult.responseResult(CommonStatusConstant.VERIFICATION_CODE_ERROR.getCode(),CommonStatusConstant.VERIFICATION_CODE_ERROR.getMessage(),null);
        }


//        log.info("判断用户是否存在，做出相对应的措施");
//        VerificationCodeDTO verificationCodeDTO = new VerificationCodeDTO();
//        verificationCodeDTO.setPassengerPhone(passengerPhone);
//        servicePassengerUserClient.loginOrRegister(verificationCodeDTO);


        String accessToken = JWTUtils.generatorToken(driverPhone, IdentityConstant.IDENTITY_DRIVER, TokenConfigConstant.ACCESS_TOKEN_KEY);
        String refreshToken = JWTUtils.generatorToken(driverPhone, IdentityConstant.IDENTITY_DRIVER, TokenConfigConstant.REFRESH_TOKEN_KEY);
        log.info("生成accessToken:{}",accessToken);
        log.info("生成refreshToken:{}",refreshToken);

        log.info("将token存储到redis中");
        String accessTokenKey = RedisPrefixUtils.generatorTokenKey(driverPhone, IdentityConstant.IDENTITY_DRIVER, TokenConfigConstant.ACCESS_TOKEN_KEY);
        String refreshTokenKey = RedisPrefixUtils.generatorTokenKey(driverPhone, IdentityConstant.IDENTITY_DRIVER, TokenConfigConstant.REFRESH_TOKEN_KEY);
        stringRedisTemplate.opsForValue().set(accessTokenKey, accessToken, 30 , TimeUnit.DAYS);
        stringRedisTemplate.opsForValue().set(refreshTokenKey, refreshToken, 31 , TimeUnit.DAYS);

        log.info("返回token");
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(accessToken);
        tokenResponse.setRefreshToken(refreshToken);


        return ResponseResult.success(tokenResponse);
    }
}
