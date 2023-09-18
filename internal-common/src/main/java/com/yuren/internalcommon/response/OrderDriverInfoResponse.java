package com.yuren.internalcommon.response;

import lombok.Data;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/17-15:59
 **/
@Data
public class OrderDriverInfoResponse {

    private Long driverId;

    private String driverPhone;

    private Long carId;
}
