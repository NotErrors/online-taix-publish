package com.yuren.seervicemap.remote;

import com.alibaba.fastjson.JSONObject;
import com.yuren.internalcommon.constant.AMapConfigConstant;
import com.yuren.internalcommon.response.ResponseResult;
import com.yuren.internalcommon.response.TerminalResponse;
import com.yuren.internalcommon.response.TrackResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/12-22:07
 **/
@Service
@Slf4j
public class TrackClinet {

    @Value("${amap.key}")
    private String amapKey;

    @Value("${amap.sid}")
    private String amapSid;

    @Autowired
    private RestTemplate restTemplate;


    public ResponseResult<TrackResponse> add(String tid){

        StringBuilder urlBuffer= new StringBuilder();
        urlBuffer.append(AMapConfigConstant.TRACK_ADD_URL);
        urlBuffer.append("?");
        urlBuffer.append("key=" + amapKey);
        urlBuffer.append("&");
        urlBuffer.append("sid=" + amapSid);
        urlBuffer.append("&");
        urlBuffer.append("tid=" + tid);
        log.info("请求的完整url：{}", urlBuffer.toString());

        ResponseEntity<String> forEntity = restTemplate.postForEntity(urlBuffer.toString(), null, String.class);
        // 解析返回结果
        String body = forEntity.getBody();
        JSONObject jsonObject = JSONObject.parseObject(body);
        TrackResponse trackResponse = jsonObject.getObject("data", TrackResponse.class);

        return ResponseResult.success(trackResponse);
    }


}
