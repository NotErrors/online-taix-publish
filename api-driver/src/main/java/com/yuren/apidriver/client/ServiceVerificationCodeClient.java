package com.yuren.apidriver.client;

import com.yuren.internalcommon.dto.NumberResponse;
import com.yuren.internalcommon.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/9-21:14
 **/
@FeignClient("service-verificationcode")
@Repository
public interface ServiceVerificationCodeClient {

    @GetMapping("/numbercode/{size}")
    public ResponseResult<NumberResponse> numbercode(@PathVariable(name = "size") Integer size);
}
