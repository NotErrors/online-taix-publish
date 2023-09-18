package com.yuren.serviceorder.controller;


import com.yuren.internalcommon.constant.HeaderParamConstants;
import com.yuren.internalcommon.request.OrderRequest;
import com.yuren.internalcommon.response.ResponseResult;
import com.yuren.serviceorder.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 拉丁
 * @since 2023-09-14
 */
@RestController
@RequestMapping("/order")
public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService;

    @PostMapping("/add")
    public ResponseResult add(@RequestBody OrderRequest orderRequest){
//            , @RequestHeader(HeaderParamConstants.DEVICE_CODE) String deviceCode) {
        return orderInfoService.add(orderRequest);
    }


}
