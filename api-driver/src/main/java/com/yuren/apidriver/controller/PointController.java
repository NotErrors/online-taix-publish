package com.yuren.apidriver.controller;

import com.yuren.apidriver.service.PointService;
import com.yuren.internalcommon.request.ApiDriverPointRequest;
import com.yuren.internalcommon.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/13-21:23
 **/
@RestController
@RequestMapping("/point")
public class PointController {

    @Autowired
    private PointService pointService;

    @PostMapping("/upload")
    public ResponseResult upload(@Valid @RequestBody ApiDriverPointRequest apiDriverPointRequest) {

        return pointService.upload(apiDriverPointRequest);

    }

}
