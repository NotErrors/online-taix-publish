package com.yuren.internalcommon.dto;

import lombok.Data;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/5-21:37
 **/
@Data
public class PriceRule {

    private String cityCode;

    private String vehicleType;

    private Double startFare;

    private Integer startMile;

    private Double unitPricePerMile;

    private Double unitPricePerMinute;

    private Integer fareVersion;

    private String fareType;

}
