package com.yuren.apipassenger.client;

import com.yuren.internalcommon.request.VerificationCodeDTO;
import com.yuren.internalcommon.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/1-21:42
 **/
@FeignClient("service-passenger-user")
public interface ServicePassengerUserClient {

    @PostMapping("/user")
    public ResponseResult loginOrRegister(@RequestBody VerificationCodeDTO verificationCodeDTO);

    /**
     * 路径相同但请求方式不同，又使用@RequeestBody注解的话，springboot会将get请求转换为post请求。
     * @param passengerPhone
     * @return
     */
    @GetMapping("/user/{phone}")
    public ResponseResult getUserByPhone(@PathVariable(name = "phone")String passengerPhone);

}
