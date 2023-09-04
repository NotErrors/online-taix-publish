package com.yuren.apipassenger.controller;

import com.yuren.apipassenger.service.ForecastPriceService;
import com.yuren.internalcommon.request.ForecastPriceDTO;
import com.yuren.internalcommon.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/3-22:18
 **/
@RestController
@Slf4j
public class ForecastPriceController {

    @Autowired
    private ForecastPriceService forecastPriceService;

    @PostMapping("/forecast-price")
    public ResponseResult forecastPrice(@RequestBody ForecastPriceDTO forecastPriceDTO) {
        String depLatitude = forecastPriceDTO.getDepLatitude();
        String depLongitude = forecastPriceDTO.getDepLongitude();
        String destLatitude = forecastPriceDTO.getDestLatitude();
        String destLongitude = forecastPriceDTO.getDestLongitude();

        log.info("起点经度：{}", depLatitude);
        log.info("起点纬度：{}", depLongitude);
        log.info("终点经度：{}", destLatitude);
        log.info("终点纬度：{}", destLongitude);

        return forecastPriceService.forecastPrice(depLongitude,depLatitude,destLongitude,destLatitude);

    }
}
