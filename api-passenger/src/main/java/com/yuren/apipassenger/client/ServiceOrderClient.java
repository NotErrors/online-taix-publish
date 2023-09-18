package com.yuren.apipassenger.client;

import com.yuren.internalcommon.request.OrderRequest;
import com.yuren.internalcommon.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/14-21:29
 **/
@FeignClient("service-order")
@Repository
public interface ServiceOrderClient {

    @PostMapping("/order/add")
    public ResponseResult add(@RequestBody OrderRequest orderRequest);
}
