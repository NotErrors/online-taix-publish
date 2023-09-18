package com.yuren.serviceDriverUser.service;

import com.yuren.internalcommon.response.ResponseResult;
import com.yuren.serviceDriverUser.mapper.DriverUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/17-11:16
 **/
@Service
public class CityDriverUserService {

    @Autowired
    private DriverUserMapper driverUserMapper;

    public ResponseResult<Boolean> isAvailableDriver(String cityCode) {
        Integer count = driverUserMapper.selectDriverUserCountByCityCode(cityCode);
        if (count > 0) {
            return ResponseResult.success(true);
        } else {
            return ResponseResult.success(false);
        }
    }

}
