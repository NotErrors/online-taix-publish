package com.yuren.apidriver.client;

import com.yuren.internalcommon.dto.Car;
import com.yuren.internalcommon.dto.DriverUser;
import com.yuren.internalcommon.response.DriverUserCheckResponse;
import com.yuren.internalcommon.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/9-10:55
 **/
@FeignClient("service-driver-user")
@Repository
public interface ServiceDriverUserClient {

    @PutMapping("/user")
    public ResponseResult updateUser(@RequestBody DriverUser driverUser);

    @GetMapping("/check-driver/{driverPhone}")
    public ResponseResult<DriverUserCheckResponse> checkDriver(@PathVariable("driverPhone") String driverPhone);

    @GetMapping("/car")
    public ResponseResult<Car> getCarById(@RequestParam Long carId);
}
