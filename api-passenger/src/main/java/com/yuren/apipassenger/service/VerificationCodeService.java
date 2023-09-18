package com.yuren.apipassenger.service;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.yuren.apipassenger.client.ServicePassengerUserClient;
import com.yuren.apipassenger.client.ServiceVerificationCodeClient;
import com.yuren.internalcommon.constant.CommonConstant;
import com.yuren.internalcommon.constant.CommonStatusConstant;
import com.yuren.internalcommon.constant.IdentityConstant;
import com.yuren.internalcommon.constant.TokenConfigConstant;
import com.yuren.internalcommon.dto.NumberResponse;
import com.yuren.internalcommon.request.VerificationCodeDTO;
import com.yuren.internalcommon.response.ResponseResult;
import com.yuren.internalcommon.response.TokenResponse;
import com.yuren.internalcommon.util.JWTUtils;
import com.yuren.internalcommon.util.RedisPrefixUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/8/20-22:58
 **/
@Service
@Slf4j
public class VerificationCodeService {

    @Autowired
    private ServiceVerificationCodeClient serviceVerificationCodeClient;

    @Autowired
    private ServicePassengerUserClient servicePassengerUserClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 发送验证码
     * @param passengerPhone 手机号
     * @return
     */
    public String verificationCode(String passengerPhone) {
        // 手机号校验
        log.info("手机号校验:{}" , passengerPhone);

        //生成验证码
        ResponseResult<NumberResponse> numbercode = serviceVerificationCodeClient.numbercode(CommonConstant.PASSENGERLENGTH);
        Integer code = numbercode.getData().getNumbercode();
        log.info("获取验证码：{}", code);

        // 将验证码存储到redis中
        String key = RedisPrefixUtils.generatorKeyByPhone(passengerPhone, IdentityConstant.IDENTITY_PASSENGER);
        stringRedisTemplate.opsForValue().set(key, code.toString(), 2, TimeUnit.MINUTES);
        log.info("将验证码存储到redis中");

        //todo 通过调用消息中间件发送短信
        // todo 也可以通过completeFuture异步调用
        log.info("通过调用消息中间件发送短信");

        return code.toString();
    }

    /**
     * 校验验证码
     * @param passengerPhone 手机号
     * @param verificationCode 验证码
     * @return
     */
    public ResponseResult checkVerificationCode(String passengerPhone, String verificationCode) {
        String key = RedisPrefixUtils.generatorKeyByPhone(passengerPhone, IdentityConstant.IDENTITY_PASSENGER);
        // todo 此处要增加一个删除逻辑，避免并发操作，可以采用lua脚本进行执行
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


        log.info("判断用户是否存在，做出相对应的措施");
        VerificationCodeDTO verificationCodeDTO = new VerificationCodeDTO();
        verificationCodeDTO.setPassengerPhone(passengerPhone);
        servicePassengerUserClient.loginOrRegister(verificationCodeDTO);


        String accessToken = JWTUtils.generatorToken(passengerPhone, IdentityConstant.IDENTITY_PASSENGER, TokenConfigConstant.ACCESS_TOKEN_KEY);
        String refreshToken = JWTUtils.generatorToken(passengerPhone, IdentityConstant.IDENTITY_PASSENGER, TokenConfigConstant.REFRESH_TOKEN_KEY);
        log.info("生成accessToken:{}",accessToken);
        log.info("生成refreshToken:{}",refreshToken);

        log.info("将token存储到redis中");
        String accessTokenKey = RedisPrefixUtils.generatorTokenKey(passengerPhone, IdentityConstant.IDENTITY_PASSENGER, TokenConfigConstant.ACCESS_TOKEN_KEY);
        String refreshTokenKey = RedisPrefixUtils.generatorTokenKey(passengerPhone, IdentityConstant.IDENTITY_PASSENGER, TokenConfigConstant.REFRESH_TOKEN_KEY);
        stringRedisTemplate.opsForValue().set(accessTokenKey, accessToken, 30 , TimeUnit.DAYS);
        stringRedisTemplate.opsForValue().set(refreshTokenKey, refreshToken, 31 , TimeUnit.DAYS);

        log.info("返回token");
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(accessToken);
        tokenResponse.setRefreshToken(refreshToken);


        return ResponseResult.success(tokenResponse);
    }
}
