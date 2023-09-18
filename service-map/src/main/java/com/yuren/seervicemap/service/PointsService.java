package com.yuren.seervicemap.service;

import com.yuren.internalcommon.request.PointRequest;
import com.yuren.internalcommon.response.ResponseResult;
import com.yuren.seervicemap.remote.PointsClinet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/12-23:09
 **/
@Service
public class PointsService {

    @Autowired
    private PointsClinet pointsClinet;

    public ResponseResult upload(PointRequest pointRequest) {
        return pointsClinet.update(pointRequest);
    }

}
