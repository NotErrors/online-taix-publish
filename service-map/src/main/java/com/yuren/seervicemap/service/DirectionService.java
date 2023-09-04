package com.yuren.seervicemap.service;

import com.yuren.internalcommon.response.DirectionResponse;
import com.yuren.internalcommon.response.ResponseResult;
import com.yuren.seervicemap.remote.MapDirectionClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/4-21:26
 **/
@Service
public class DirectionService {

    @Autowired
    private MapDirectionClient mapDirectionClient;

    /**
     * 根据起点终点的经纬度，获取距离和时长
     * @param depLatitude
     * @param depLongitude
     * @param destLatitude
     * @param destLongitude
     * @return
     */
    public ResponseResult driving(String depLongitude, String depLatitude, String destLongitude, String destLatitude) {

        mapDirectionClient.direction(depLongitude, depLatitude, destLongitude, destLatitude);

        DirectionResponse directionResponse = new DirectionResponse();
        directionResponse.setDistance(123);
        directionResponse.setDuration(456);

        return ResponseResult.success(directionResponse);
    }
}
