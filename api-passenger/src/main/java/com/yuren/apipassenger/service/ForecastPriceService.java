package com.yuren.apipassenger.service;

import com.yuren.apipassenger.client.ServicePriceClient;
import com.yuren.internalcommon.request.ForecastPriceDTO;
import com.yuren.internalcommon.response.PriceResponse;
import com.yuren.internalcommon.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/3-22:23
 **/
@Service
public class ForecastPriceService {

    @Autowired
    private ServicePriceClient servicePriceClient;

    /**
     * 计算起点到终点的经纬度
     * @param depLatitude 起点经度
     * @param depLongitude 起点纬度
     * @param destLatitude 终点经度
     * @param destLongitude 终点纬度
     * @return
     */
    public ResponseResult forecastPrice(String depLongitude, String depLatitude, String destLongitude, String destLatitude) {

        ForecastPriceDTO forecastPriceDTO = new ForecastPriceDTO();
        forecastPriceDTO.setDepLongitude(depLongitude);
        forecastPriceDTO.setDepLatitude(depLatitude);
        forecastPriceDTO.setDestLongitude(destLongitude);
        forecastPriceDTO.setDestLatitude(destLatitude);

        ResponseResult<PriceResponse> priceResponseResponseResult = servicePriceClient.forecastPrice(forecastPriceDTO);
        return ResponseResult.success(priceResponseResponseResult.getData());
    }
}
