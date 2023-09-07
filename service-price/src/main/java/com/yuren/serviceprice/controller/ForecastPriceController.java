package com.yuren.serviceprice.controller;

import com.yuren.internalcommon.request.ForecastPriceDTO;
import com.yuren.internalcommon.response.PriceResponse;
import com.yuren.internalcommon.response.ResponseResult;
import com.yuren.serviceprice.service.ForecastPriceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/3-22:53
 **/
@RestController
@Slf4j
public class ForecastPriceController {

    @Autowired
    private ForecastPriceService forecastPriceService;

    @PostMapping("/forecast-price")
    public ResponseResult<PriceResponse> forecastPrice(@RequestBody ForecastPriceDTO forecastPriceDTO) {
        String depLatitude = forecastPriceDTO.getDepLatitude();
        String depLongitude = forecastPriceDTO.getDepLongitude();
        String destLatitude = forecastPriceDTO.getDestLatitude();
        String destLongitude = forecastPriceDTO.getDestLongitude();


        return forecastPriceService.forecastPrice(depLongitude,depLatitude,destLongitude,destLatitude);
    }

}
