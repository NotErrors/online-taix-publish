package com.yuren.apiboss.controller;

import com.yuren.apiboss.service.ApiBossService;
import com.yuren.internalcommon.dto.Car;
import com.yuren.internalcommon.dto.DriverUser;
import com.yuren.internalcommon.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/9-10:06
 **/
@RestController
public class ApiBossController {

    @Autowired
    private ApiBossService apiBossService;

    @PostMapping("/driver-user")
    public ResponseResult addDriverUser(@RequestBody DriverUser driverUser) {
        apiBossService.addDriverUser(driverUser);
        return ResponseResult.success();
    }

    @PutMapping("/driver-user")
    public ResponseResult updateDriverUser(@RequestBody DriverUser driverUser) {
        return apiBossService.updateDriverUser(driverUser);
    }

    @PostMapping("/car")
    public ResponseResult addCar(@RequestBody Car car) {
        return apiBossService.addCar(car);
    }

}
