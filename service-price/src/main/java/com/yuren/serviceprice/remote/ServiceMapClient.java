package com.yuren.serviceprice.remote;

import com.yuren.internalcommon.request.ForecastPriceDTO;
import com.yuren.internalcommon.response.DirectionResponse;
import com.yuren.internalcommon.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/4-23:02
 **/
@FeignClient("service-map")
public interface ServiceMapClient {

    @GetMapping("/direction/driving")
    public ResponseResult<DirectionResponse> driving(@RequestBody ForecastPriceDTO forecastPriceDTO);
}
