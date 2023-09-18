package com.yuren.seervicemap.remote;

import com.alibaba.fastjson.JSONObject;
import com.yuren.internalcommon.constant.AMapConfigConstant;
import com.yuren.internalcommon.request.PointRequest;
import com.yuren.internalcommon.response.ResponseResult;
import com.yuren.internalcommon.response.TrackResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/12-23:05
 **/
@Service
@Slf4j
public class PointsClinet {

    @Value("${amap.key}")
    private String amapKey;

    @Value("${amap.sid}")
    private String amapSid;

    @Autowired
    private RestTemplate restTemplate;


    @SneakyThrows
    public ResponseResult update(PointRequest pointRequest){

        StringBuilder urlBuffer= new StringBuilder();
        urlBuffer.append(AMapConfigConstant.POINT_UPLOAD_URL);
        urlBuffer.append("?");
        urlBuffer.append("key=" + amapKey);
        urlBuffer.append("&");
        urlBuffer.append("sid=" + amapSid);
        urlBuffer.append("&");
        urlBuffer.append("tid=" + pointRequest.getTid());
        urlBuffer.append("&");
        urlBuffer.append("trid=" + pointRequest.getTrid());
        urlBuffer.append("&");
        urlBuffer.append("points=" + URLEncoder.encode(JSONObject.toJSONString(pointRequest.getPoints()),"UTF-8"));
        log.info("请求的完整url：{}", urlBuffer.toString());
        ResponseEntity<String> forEntity = restTemplate.postForEntity(URI.create(urlBuffer.toString()), null, String.class);
        log.info("返回结果：{}", forEntity.getBody());

        return ResponseResult.success();
    }



}
