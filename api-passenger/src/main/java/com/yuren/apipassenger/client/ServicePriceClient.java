package com.yuren.apipassenger.client;

import com.yuren.internalcommon.request.ForecastPriceDTO;
import com.yuren.internalcommon.response.PriceResponse;
import com.yuren.internalcommon.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/5-22:33
 **/
@FeignClient("service-price")
public interface ServicePriceClient {

    @PostMapping("/forecast-price")
    public ResponseResult<PriceResponse> forecastPrice(@RequestBody ForecastPriceDTO forecastPriceDTO) ;
}
