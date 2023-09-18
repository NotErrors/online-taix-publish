package com.yuren.apidriver.client;

import com.yuren.internalcommon.request.PointRequest;
import com.yuren.internalcommon.response.ResponseResult;
import com.yuren.internalcommon.response.TerminalResponse;
import com.yuren.internalcommon.response.TrackResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/13-21:42
 **/
@FeignClient("service-map")
@Repository
public interface ServiceMapClient {

    @PostMapping("/terminal/add")
    public ResponseResult<TerminalResponse> addTerminal(@RequestParam String name);

    @PostMapping("/track/add")
    public ResponseResult<TrackResponse> addTrack(@RequestParam String tid);

    @PostMapping("/point/upload")
    public ResponseResult upload(@RequestBody  PointRequest pointRequest);
}
