package com.yuren.apiboss.client;

import com.yuren.internalcommon.dto.Car;
import com.yuren.internalcommon.dto.DriverCarBindingRelationship;
import com.yuren.internalcommon.dto.DriverUser;
import com.yuren.internalcommon.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/9-10:08
 **/
@FeignClient("service-driver-user")
@Repository
public interface ServiceDriverUserClient {

    @PostMapping("/user")
    public ResponseResult addUser(@RequestBody DriverUser driverUser);

    @PutMapping("/user")
    public ResponseResult updateUser(@RequestBody DriverUser driverUser);

    @PostMapping("/car")
    public ResponseResult addCar(@RequestBody Car car);

    @PostMapping("/driver-car-binding-relationship/bind")
    public ResponseResult bind(@RequestBody DriverCarBindingRelationship driverCarBindingRelationship);

    @PostMapping("/driver-car-binding-relationship/unbind")
    public ResponseResult unbind(@RequestBody DriverCarBindingRelationship driverCarBindingRelationship);
}
