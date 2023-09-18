package com.yuren.seervicemap.service;

import com.yuren.internalcommon.response.ResponseResult;
import com.yuren.internalcommon.response.TrackResponse;
import com.yuren.seervicemap.remote.TrackClinet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/12-22:07
 **/
@Service
public class TrackService {

    @Autowired
    private TrackClinet trackClinet;

    public ResponseResult<TrackResponse> add(String tid) {
        return trackClinet.add(tid);
    }

}
