package com.yuren.serviceorder.client;

import com.yuren.internalcommon.dto.PriceRule;
import com.yuren.internalcommon.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/15-22:50
 **/
@FeignClient("service-price")
@Repository
public interface ServicePriceClient {

    @GetMapping("/price-rule/is-new")
    public ResponseResult<Boolean> isNew(@RequestParam("fareType") String fareType, @RequestParam("fareVersion") Integer fareVersion) ;

    @GetMapping("/price-rule/is-exists")
    public ResponseResult<Boolean> ifExists(@RequestBody PriceRule priceRule) ;


    }
