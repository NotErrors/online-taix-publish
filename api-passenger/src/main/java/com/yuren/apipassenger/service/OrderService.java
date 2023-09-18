package com.yuren.apipassenger.service;

import com.yuren.apipassenger.client.ServiceOrderClient;
import com.yuren.internalcommon.request.OrderRequest;
import com.yuren.internalcommon.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/14-21:30
 **/
@Service
public class OrderService {

    @Autowired
    private ServiceOrderClient serviceOrderClient;

    public ResponseResult addOrder(OrderRequest orderRequest) {
        return serviceOrderClient.add(orderRequest);
    }
}
