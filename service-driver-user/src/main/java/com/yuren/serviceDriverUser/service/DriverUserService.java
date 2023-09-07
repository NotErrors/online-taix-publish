package com.yuren.serviceDriverUser.service;

import com.yuren.internalcommon.dto.DriverUser;
import com.yuren.internalcommon.response.ResponseResult;
import com.yuren.serviceDriverUser.mapper.DriverUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/7-22:07
 **/
@Service
public class DriverUserService {

    @Autowired
    private DriverUserMapper driverUserMapper;


    public ResponseResult addDriverUser(DriverUser driverUser) {
        LocalDateTime now = LocalDateTime.now();
        driverUser.setGmtCreate(now);
        driverUser.setGmtModified(now);

        driverUserMapper.insert(driverUser);
        return ResponseResult.success();
    }
}
