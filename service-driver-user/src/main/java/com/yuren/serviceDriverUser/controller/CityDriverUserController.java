package com.yuren.serviceDriverUser.controller;

import com.yuren.internalcommon.response.ResponseResult;
import com.yuren.serviceDriverUser.service.CityDriverUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/17-11:18
 **/
@RestController
@RequestMapping("/city-driver")
public class CityDriverUserController {

    @Autowired
    private CityDriverUserService cityDriverUserService;

    @GetMapping("/is-alailable-driver")
    public ResponseResult isAlailableDriver(String cityCode) {
        return cityDriverUserService.isAvailableDriver(cityCode);
    }

}
