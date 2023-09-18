package com.yuren.serviceDriverUser.remote;

import com.yuren.internalcommon.response.ResponseResult;
import com.yuren.internalcommon.response.TerminalResponse;
import com.yuren.internalcommon.response.TrackResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/12-21:40
 **/
@FeignClient("service-map")
@Repository
public interface ServiceMapService {

    @PostMapping("/terminal/add")
    public ResponseResult<TerminalResponse> addTerminal(@RequestParam String name , @RequestParam Long desc);

    @PostMapping("/track/add")
    public ResponseResult<TrackResponse> addTrack(@RequestParam String tid);
}
