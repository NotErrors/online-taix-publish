package com.yuren.internalcommon.request;

import lombok.Data;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/3-22:19
 **/
@Data
public class ForecastPriceDTO {

    /**
     * 起步点纬度
     */
    private String depLatitude;
    /**
     * 起步点经度
     */
    private String depLongitude;
    /**
     * 终点纬度
     */
    private String destLatitude;
    /**
     * 终点经度
     */
    private String destLongitude;
}
