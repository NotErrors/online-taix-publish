package com.yuren.apipassenger.service;

import com.yuren.apipassenger.client.ServicePassengerUserClient;
import com.yuren.internalcommon.constant.CommonStatusConstant;
import com.yuren.internalcommon.dto.PassengerUser;
import com.yuren.internalcommon.dto.TokenResult;
import com.yuren.internalcommon.response.ResponseResult;
import com.yuren.internalcommon.util.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/3-16:41
 **/
@Service
@Slf4j
public class UserService {

    @Autowired
    private ServicePassengerUserClient passengerUserClient;

    public ResponseResult getUserInfoByToken(String accessToken) {
        log.info("获取tokn:{}",accessToken);

        log.info("解析token获取手机号码");
        TokenResult tokenResult = JWTUtils.parseToken(accessToken);
        if (Objects.isNull(tokenResult)){
            return ResponseResult.responseResult(CommonStatusConstant.TOKEN_CHECK_ERROR.getCode(), CommonStatusConstant.TOKEN_CHECK_ERROR.getMessage());
        }
        log.info("通过手机号码获取用户信息");
        String phone = tokenResult.getPhone();
        ResponseResult responseResult = passengerUserClient.getUserByPhone(phone);

        log.info("封装用户信息");
        return ResponseResult.success(responseResult.getData());
    }
}
