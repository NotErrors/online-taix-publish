package com.yuren.serviceorder.client;

import com.yuren.internalcommon.response.ResponseResult;
import com.yuren.internalcommon.response.TerminalResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/17-11:58
 **/
@FeignClient("service-map")
@Repository
public interface ServiceMapClient {

    @GetMapping("/terminal/aroundsearch")
    public ResponseResult<List<TerminalResponse>> teerminalAroundsearch(@RequestParam  String center, @RequestParam Integer radius);

}
