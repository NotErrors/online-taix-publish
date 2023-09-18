package com.yuren.seervicemap.controller;

import com.yuren.internalcommon.request.PointRequest;
import com.yuren.internalcommon.response.ResponseResult;
import com.yuren.seervicemap.service.PointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/12-23:09
 **/
@RestController
@RequestMapping("/point")
public class PointController {

    @Autowired
    private PointsService pointsService;

    @PostMapping("/upload")
    public ResponseResult upload(@RequestBody PointRequest pointRequest) {
        return pointsService.upload(pointRequest);
    }

}
