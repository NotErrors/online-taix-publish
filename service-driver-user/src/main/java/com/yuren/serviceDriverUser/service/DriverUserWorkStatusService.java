package com.yuren.serviceDriverUser.service;

import com.yuren.internalcommon.dto.DriverUserWorkStatus;
import com.yuren.internalcommon.response.ResponseResult;
import com.yuren.serviceDriverUser.mapper.DriverUserWorkStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/9-22:23
 **/
@Service
public class DriverUserWorkStatusService {

    @Autowired
    private DriverUserWorkStatusMapper driverUserWorkStatusMapper;

    public ResponseResult updateDriverUserWorkStatus(DriverUserWorkStatus driverUserWorkStatus) {
        Long driverId = driverUserWorkStatus.getDriverId();
        Map<String, Object> map = new HashMap<>();
        map.put("driver_id", driverId);
        List<DriverUserWorkStatus> driverUserWorkStatuses = driverUserWorkStatusMapper.selectByMap(map);
        DriverUserWorkStatus driverUserWork = driverUserWorkStatuses.get(0);

        driverUserWork.setWorkStatus(driverUserWorkStatus.getWorkStatus());
        driverUserWorkStatusMapper.updateById(driverUserWork);
        return ResponseResult.success();
    }
}
