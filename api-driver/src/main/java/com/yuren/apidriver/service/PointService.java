package com.yuren.apidriver.service;

import com.yuren.apidriver.client.ServiceDriverUserClient;
import com.yuren.apidriver.client.ServiceMapClient;
import com.yuren.internalcommon.dto.Car;
import com.yuren.internalcommon.request.ApiDriverPointRequest;
import com.yuren.internalcommon.request.PointRequest;
import com.yuren.internalcommon.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/13-21:25
 **/
@Service
public class PointService {

    @Autowired
    private ServiceDriverUserClient  serviceDriverUserClient;

    @Autowired
    private ServiceMapClient serviceMapClient;

    public ResponseResult upload(ApiDriverPointRequest apiDriverPointRequest) {

        Long carId = apiDriverPointRequest.getCarId();

        ResponseResult<Car> carResponse = serviceDriverUserClient.getCarById(carId);
        Car car = carResponse.getData();
        String tid = car.getTid();
        String trid = car.getTrid();

        PointRequest pointRequest = new PointRequest();
        pointRequest.setTid(tid);
        pointRequest.setTrid(trid);
        pointRequest.setPoints(apiDriverPointRequest.getPoints());

        return serviceMapClient.upload(pointRequest);
    }
}
