package com.yuren.serviceDriverUser.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuren.internalcommon.constant.CommonStatusConstant;
import com.yuren.internalcommon.constant.DriverCarConstants;
import com.yuren.internalcommon.dto.DriverCarBindingRelationship;
import com.yuren.internalcommon.dto.DriverUser;
import com.yuren.internalcommon.dto.DriverUserWorkStatus;
import com.yuren.internalcommon.response.OrderDriverInfoResponse;
import com.yuren.internalcommon.response.ResponseResult;
import com.yuren.serviceDriverUser.mapper.DriverCarBindingRelationshipMapper;
import com.yuren.serviceDriverUser.mapper.DriverUserMapper;
import com.yuren.serviceDriverUser.mapper.DriverUserWorkStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Driver;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/7-22:07
 **/
@Service
public class DriverUserService {

    @Autowired
    private DriverUserMapper driverUserMapper;

    @Autowired
    private DriverUserWorkStatusMapper driverUserWorkStatusMapper;

    @Autowired
    private DriverCarBindingRelationshipMapper driverCarBindingRelationshipMapper;


    public ResponseResult addDriverUser(DriverUser driverUser) {
        LocalDateTime now = LocalDateTime.now();
        driverUser.setGmtCreate(now);
        driverUser.setGmtModified(now);

        driverUserMapper.insert(driverUser);

        // 添加司机出现状态
        DriverUserWorkStatus driverUserWorkStatus = new DriverUserWorkStatus();
        driverUserWorkStatus.setDriverId(driverUser.getId());
        driverUserWorkStatus.setWorkStatus(DriverCarConstants.DRIVER_WORK_STATUS_STOP);
        driverUserWorkStatus.setGmtCreate(now);
        driverUserWorkStatusMapper.insert(driverUserWorkStatus);

        return ResponseResult.success();
    }


    public ResponseResult updateDriverUseer(DriverUser driverUser) {
        LocalDateTime now = LocalDateTime.now();
        driverUser.setGmtModified(now);
        driverUserMapper.updateById(driverUser);

        return ResponseResult.success();
    }

    public ResponseResult<DriverUser> checkUserByPhone(String driverPhone) {
        Map<String, Object> map = new HashMap<>();
        map.put("driver_phone", driverPhone);
        map.put("state", DriverCarConstants.DRIVER_STATE_VALID);

        List<DriverUser> driverUsers = driverUserMapper.selectByMap(map);
        if (driverUsers == null || driverUsers.isEmpty()) {
            return ResponseResult.success();
        }
        return ResponseResult.success(driverUsers.get(0));
    }

    /**
     * 判断当前车辆的司机是否在出车状态
     * @param carId
     * @return
     */
    public ResponseResult<OrderDriverInfoResponse> getAvaliableDriver(Long carId) {
        QueryWrapper<DriverCarBindingRelationship> wrapper = new QueryWrapper<>();
        wrapper.eq("car_id", carId);
        wrapper.eq("bind_status", DriverCarConstants.DRIVER_CAR_BIND);
        DriverCarBindingRelationship driverCarBindingRelationship = driverCarBindingRelationshipMapper.selectOne(wrapper);
        if (null == driverCarBindingRelationship) {
            return ResponseResult.fail(CommonStatusConstant.DRIVER_CAR_BIND_NOT_EXISTS.getCode(), CommonStatusConstant.DRIVER_CAR_BIND_NOT_EXISTS.getMessage());
        }

        Long driverId = driverCarBindingRelationship.getDriverId();
        QueryWrapper<DriverUserWorkStatus> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("driver_id", driverId);
        queryWrapper.eq("work_status", DriverCarConstants.DRIVER_WORK_STATUS_START);
        DriverUserWorkStatus driverUserWorkStatus = driverUserWorkStatusMapper.selectOne(queryWrapper);
        if (null == driverUserWorkStatus) {
            return ResponseResult.fail(CommonStatusConstant.DRIVER_NOT_EXISTS.getCode(), CommonStatusConstant.DRIVER_NOT_EXISTS.getMessage());
        }

        DriverUser driverUser = driverUserMapper.selectById(driverId);
        OrderDriverInfoResponse response= new OrderDriverInfoResponse();
        response.setCarId(carId);
        response.setDriverId(driverId);
        response.setDriverPhone(driverUser.getDriverPhone());

        return ResponseResult.success(response);
    }
}
