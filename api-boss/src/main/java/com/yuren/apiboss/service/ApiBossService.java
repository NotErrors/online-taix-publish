package com.yuren.apiboss.service;

import com.yuren.apiboss.client.ServiceDriverUserClient;
import com.yuren.internalcommon.dto.Car;
import com.yuren.internalcommon.dto.DriverUser;
import com.yuren.internalcommon.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/9-10:07
 **/

@Service
public class ApiBossService {

    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    public ResponseResult addDriverUser(DriverUser driverUser){
        serviceDriverUserClient.addUser(driverUser);
        return ResponseResult.success();
    }

    public ResponseResult updateDriverUser(DriverUser driverUser) {
        return serviceDriverUserClient.updateUser(driverUser);
    }

    public ResponseResult addCar(Car car) {
        return serviceDriverUserClient.addCar(car);
    }
}
