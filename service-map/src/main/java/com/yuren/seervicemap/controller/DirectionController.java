package com.yuren.seervicemap.controller;

import com.yuren.internalcommon.request.ForecastPriceDTO;
import com.yuren.internalcommon.response.ResponseResult;
import com.yuren.seervicemap.service.DirectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/4-21:24
 **/

@RestController
@RequestMapping("/direction")
public class DirectionController {

    @Autowired
    private DirectionService directionService;

    @GetMapping("/driving")
    public ResponseResult driving(@RequestBody ForecastPriceDTO forecastPriceDTO) {
        String depLatitude = forecastPriceDTO.getDepLatitude();
        String depLongitude = forecastPriceDTO.getDepLongitude();
        String destLatitude = forecastPriceDTO.getDestLatitude();
        String destLongitude = forecastPriceDTO.getDestLongitude();

        return directionService.driving(depLongitude,depLatitude,destLongitude,destLatitude);

    }
}
