package com.yuren.serviceDriverUser.service;

import com.yuren.internalcommon.dto.Car;
import com.yuren.internalcommon.response.ResponseResult;
import com.yuren.internalcommon.response.TerminalResponse;
import com.yuren.internalcommon.response.TrackResponse;
import com.yuren.serviceDriverUser.mapper.CarMapper;
import com.yuren.serviceDriverUser.remote.ServiceMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/9-11:23
 **/

@Service
public class CarService {

    @Autowired
    private CarMapper carMapper;

    @Autowired
    private ServiceMapService serviceMapService;

    public ResponseResult addCar(Car car) {

        LocalDateTime now = LocalDateTime.now();
        car.setGmtCreate(now);
        car.setGmtModified(now);
        carMapper.insert(car);

        // 获取车牌号，生成对应的终端id
        String vehicleNo = car.getVehicleNo();
        ResponseResult<TerminalResponse> responseResult = serviceMapService.addTerminal(vehicleNo, car.getId());
        TerminalResponse data = responseResult.getData();
        car.setTid(data.getTid());

        // 生成轨迹id trid
        ResponseResult<TrackResponse> trackResponseResult = serviceMapService.addTrack(data.getTid());
        TrackResponse trackResponse = trackResponseResult.getData();
        car.setTrid(trackResponse.getTrid());
        car.setTrname(trackResponse.getTrname());

        carMapper.updateById(car );
        return ResponseResult.success(responseResult);
    }


    public ResponseResult<Car> getCarById(Long carId) {

        Car car = carMapper.selectById(carId);
        return ResponseResult.success(car);
    }
}
