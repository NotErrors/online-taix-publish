package com.yuren.servicepassengeruser.service;

import com.yuren.internalcommon.constant.CommonStatusConstant;
import com.yuren.internalcommon.response.ResponseResult;
import com.yuren.internalcommon.dto.PassengerUser;
import com.yuren.servicepassengeruser.mapper.PassengerUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/8/31-22:18
 **/
@Service
@Slf4j
public class UserService {

    @Autowired
    private PassengerUserMapper passengerUserMapper;

    public ResponseResult loginOrRegister(String passengerPhone) {
        log.info("接受用户手机号：{}", passengerPhone);

        log.info("判断用户是否存在");
        Map<String, Object> map = new HashMap<>();
        map.put("passenger_phone", passengerPhone);
        List<PassengerUser> passengerUsers = passengerUserMapper.selectByMap(map);
        if (!passengerUsers.isEmpty()) {
            log.info("用户信息:{}",passengerUsers.get(0));
        } else {
            log.info("不存在则创建新用户");
            PassengerUser user = new PassengerUser();
            user.setPassengerName("李四");
            user.setPassengerGender((byte)0);
            user.setPassengerPhone(passengerPhone);
            user.setState((byte)0);

            LocalDateTime now = LocalDateTime.now();
            user.setGmtCreate(now);
            user.setGmtModified(now);
            passengerUserMapper.insert(user);
        }
        log.info("返回用户信息");

        return ResponseResult.success();
    }

    public ResponseResult getUserByPhone(String passengerPhone) {
        Map<String, Object> map = new HashMap<>();
        map.put("passenger_phone", passengerPhone);
        List<PassengerUser> passengerUsers = passengerUserMapper.selectByMap(map);
        if (!passengerUsers.isEmpty()) {
            PassengerUser passengerUser = passengerUsers.get(0);
            return ResponseResult.success(passengerUser);
        }
        return ResponseResult.responseResult(CommonStatusConstant.USER_ERROR_EXIST.getCode(), CommonStatusConstant.USER_ERROR_EXIST.getMessage());
    }
}
