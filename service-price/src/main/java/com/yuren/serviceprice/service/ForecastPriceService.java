package com.yuren.serviceprice.service;

import com.yuren.internalcommon.request.ForecastPriceDTO;
import com.yuren.internalcommon.response.PriceResponse;
import com.yuren.internalcommon.response.ResponseResult;
import com.yuren.serviceprice.remote.ServiceMapClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/3-22:54
 **/
@Service
public class ForecastPriceService {

    @Autowired
    private ServiceMapClient serviceMapClient;

    /**
     * 计算起点到终点的经纬度
     * @param depLatitude 起点纬度
     * @param depLongitude 起点经度
     * @param destLatitude 终点纬度
     * @param destLongitude 终点经度
     * @return
     */
    public ResponseResult forecastPrice(String depLongitude, String depLatitude, String destLongitude, String destLatitude) {

        // 重新封装ForecastPriceDTO，是因为参数可能这中途需要进行计算等其他原因
        // 故controller层将参数进行拆分，然后在要请求时进行封装
        ForecastPriceDTO forecastPriceDTO = new ForecastPriceDTO();
        forecastPriceDTO.setDepLongitude(depLongitude);
        forecastPriceDTO.setDepLatitude(depLatitude);
        forecastPriceDTO.setDestLongitude(destLongitude);
        forecastPriceDTO.setDestLatitude(destLatitude);
        ResponseResult responseResult = serviceMapClient.driving(forecastPriceDTO);

        PriceResponse priceResponse = new PriceResponse();
        priceResponse.setPrice(12.23);
        return ResponseResult.success(priceResponse);
    }

}
