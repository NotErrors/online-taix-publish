package com.yuren.serviceDriverUser.controller;

import com.yuren.internalcommon.constant.DriverCarConstants;
import com.yuren.internalcommon.dto.DriverUser;
import com.yuren.internalcommon.response.DriverUserCheckResponse;
import com.yuren.internalcommon.response.OrderDriverInfoResponse;
import com.yuren.internalcommon.response.ResponseResult;
import com.yuren.serviceDriverUser.mapper.DriverUserMapper;
import com.yuren.serviceDriverUser.service.DriverUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/7-22:08
 **/

@RestController
public class DriverUserController {

    @Autowired
    private DriverUserService driverUserService;

    @PostMapping("/user")
    public ResponseResult addUser(@RequestBody DriverUser driverUser) {

        return driverUserService.addDriverUser(driverUser);
    }

    @PutMapping("/user")
    public ResponseResult updateUser(@RequestBody DriverUser driverUser) {
        return driverUserService.updateDriverUseer(driverUser);
    }

    @GetMapping("/check-driver/{driverPhone}")
    public ResponseResult checkDriver(@PathVariable("driverPhone") String driverPhone) {
        ResponseResult<DriverUser> driverUserResponseResult = driverUserService.checkUserByPhone(driverPhone);
        DriverUser driverUserDB = driverUserResponseResult.getData();

        DriverUserCheckResponse response = new DriverUserCheckResponse();
        int ifExists = DriverCarConstants.DRIVER_NOT_EXISTS;
        if (driverUserDB != null) {
            ifExists = DriverCarConstants.DRIVER_EXISTS;
            response.setDriverPhone(driverUserDB.getDriverPhone());
        } else {
            response.setDriverPhone(driverPhone);
        }
        response.setIfExists(ifExists);
        return ResponseResult.success(response);
    }

//    @Autowired
//    private DriverUserMapper driverUserMapper;
//    @GetMapping("/test-xml")
//    public Integer textXml(String cityCode) {
//        Integer integer = driverUserMapper.select1(cityCode);
//        return integer;
//    }

    @GetMapping("/get-avaliable-drive/{carId}")
    public ResponseResult<OrderDriverInfoResponse> getAvaliableDriver(@PathVariable("carId") Long carId) {
        return driverUserService.getAvaliableDriver(carId);
    }
}
