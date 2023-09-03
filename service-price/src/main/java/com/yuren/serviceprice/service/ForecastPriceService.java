package com.yuren.serviceprice.service;

import com.yuren.internalcommon.response.PriceResponse;
import com.yuren.internalcommon.response.ResponseResult;
import org.springframework.stereotype.Service;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/3-22:54
 **/
@Service
public class ForecastPriceService {

    /**
     * 计算起点到终点的经纬度
     * @param depLatitude 起点经度
     * @param depLongitude 起点纬度
     * @param destLatitude 终点经度
     * @param destLongitude 终点纬度
     * @return
     */
    public ResponseResult forecastPrice(String depLatitude, String depLongitude, String destLatitude, String destLongitude) {

        PriceResponse priceResponse = new PriceResponse();
        priceResponse.setPrice(12.23);
        return ResponseResult.success(priceResponse);
    }

}
