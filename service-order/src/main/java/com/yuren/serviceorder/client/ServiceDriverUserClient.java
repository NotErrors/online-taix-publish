package com.yuren.serviceorder.client;

import com.yuren.internalcommon.response.OrderDriverInfoResponse;
import com.yuren.internalcommon.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/17-11:20
 **/
@FeignClient("service-driver-user")
@Repository
public interface ServiceDriverUserClient {

    @GetMapping("/city-driver/is-alailable-driver")
    public ResponseResult<Boolean> isAlailableDriver(@RequestParam String cityCode) ;

    @GetMapping("/get-avaliable-drive/{carId}")
    public ResponseResult<OrderDriverInfoResponse> getAvaliableDriver(@PathVariable("carId") Long carId);

}
