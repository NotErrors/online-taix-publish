package com.yuren.seervicemap.controller;

import com.yuren.internalcommon.response.ResponseResult;
import com.yuren.seervicemap.service.ServiceFromMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/11-22:59
 **/
@RestController
@RequestMapping("/service")
public class ServiceController {

    @Autowired
    private ServiceFromMapService serviceFromMapService;

    @PostMapping("/add")
    public ResponseResult add(@RequestParam("name") String name) {

        return serviceFromMapService.add(name);
    }
}
