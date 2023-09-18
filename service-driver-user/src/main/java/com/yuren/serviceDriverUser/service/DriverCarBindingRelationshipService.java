package com.yuren.serviceDriverUser.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuren.internalcommon.constant.CommonStatusConstant;
import com.yuren.internalcommon.constant.DriverCarConstants;
import com.yuren.internalcommon.response.ResponseResult;
import com.yuren.internalcommon.dto.DriverCarBindingRelationship;
import com.yuren.serviceDriverUser.mapper.DriverCarBindingRelationshipMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/9-11:58
 **/
@Service
@Slf4j
public class DriverCarBindingRelationshipService {

    @Autowired
    private DriverCarBindingRelationshipMapper driverCarBindingRelationshipMapper;

    public ResponseResult bind(DriverCarBindingRelationship driverCarBindingRelationship) {
        log.info("判断车辆和司机是否已经绑定");
        QueryWrapper<DriverCarBindingRelationship> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("driver_id", driverCarBindingRelationship.getDriverId())
                .eq("car_id", driverCarBindingRelationship.getCarId())
                .eq("bind_state", DriverCarConstants.DRIVER_CAR_BIND);

        Integer count = driverCarBindingRelationshipMapper.selectCount(queryWrapper);
        if (count > 0) {
            return ResponseResult.fail(CommonStatusConstant.DRIVER_CAR_BIND_EXISTS.getCode(), CommonStatusConstant.DRIVER_CAR_BIND_EXISTS.getMessage());
        }

        log.info("判断司机是否绑定了其他车辆");
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("driver_id", driverCarBindingRelationship.getDriverId())
                .eq("bind_state", DriverCarConstants.DRIVER_CAR_BIND);

        count = driverCarBindingRelationshipMapper.selectCount(queryWrapper);
        if (count > 0) {
            return ResponseResult.fail(CommonStatusConstant.DRIVER_BIND_EXISTS.getCode(), CommonStatusConstant.DRIVER_BIND_EXISTS.getMessage());
        }

        log.info("判断车辆是否绑定了其他司机");
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("car_id", driverCarBindingRelationship.getCarId())
                .eq("bind_state", DriverCarConstants.DRIVER_CAR_BIND);

        count = driverCarBindingRelationshipMapper.selectCount(queryWrapper);
        if (count > 0) {
            return ResponseResult.fail(CommonStatusConstant.CAR_BIND_EXISTS.getCode(), CommonStatusConstant.CAR_BIND_EXISTS.getMessage());
        }

        log.info("进行绑定");
        driverCarBindingRelationship.setBindingTime(LocalDateTime.now());
        driverCarBindingRelationship.setBindState(DriverCarConstants.DRIVER_CAR_BIND);

        driverCarBindingRelationshipMapper.insert(driverCarBindingRelationship);
        return ResponseResult.success();
    }

    public ResponseResult unbind(DriverCarBindingRelationship driverCarBindingRelationship) {
        log.info("判断司机和车辆的绑定关系存不存在");
        Map<String, Object> map = new HashMap<>();
        map.put("driver_id", driverCarBindingRelationship.getDriverId());
        map.put("car_id", driverCarBindingRelationship.getCarId());
        map.put("bind_state", DriverCarConstants.DRIVER_CAR_BIND);

        List<DriverCarBindingRelationship> driverCarBindingRelationships = driverCarBindingRelationshipMapper.selectByMap(map);
        if (driverCarBindingRelationships ==null || driverCarBindingRelationships.isEmpty()) {
            return ResponseResult.fail(CommonStatusConstant.DRIVER_CAR_BIND_NOT_EXISTS.getCode(), CommonStatusConstant.DRIVER_CAR_BIND_NOT_EXISTS.getMessage());
        }
        log.info("判断司机和车辆的关系解绑");
        DriverCarBindingRelationship relationship = driverCarBindingRelationships.get(0);
        relationship.setBindState(DriverCarConstants.DRIVER_CAR_UNBIND);
        relationship.setUnBindingTime(LocalDateTime.now());

        driverCarBindingRelationshipMapper.updateById(relationship);

        return ResponseResult.success();
    }
}
