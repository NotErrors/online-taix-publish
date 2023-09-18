package com.yuren.internalcommon.response;

import lombok.Data;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/9-17:37
 **/
@Data
public class DriverUserCheckResponse {

    private String driverPhone;

    private int ifExists;

}
