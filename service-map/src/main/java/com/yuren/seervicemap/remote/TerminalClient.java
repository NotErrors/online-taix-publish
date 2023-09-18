package com.yuren.seervicemap.remote;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yuren.internalcommon.constant.AMapConfigConstant;
import com.yuren.internalcommon.response.ResponseResult;
import com.yuren.internalcommon.response.ServiceResponse;
import com.yuren.internalcommon.response.TerminalResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/12-21:15
 **/
@Service
@Slf4j
public class TerminalClient {

    @Value("${amap.key}")
    private String amapKey;

    @Value("${amap.sid}")
    private String amapSid;

    @Autowired
    private RestTemplate restTemplate;


    public ResponseResult<TerminalResponse> add(String name, Long desc){

        StringBuilder urlBuffer= new StringBuilder();
        urlBuffer.append(AMapConfigConstant.TERMINAL_ADD_URL);
        urlBuffer.append("?");
        urlBuffer.append("key=" + amapKey);
        urlBuffer.append("&");
        urlBuffer.append("sid=" + amapSid);
        urlBuffer.append("&");
        urlBuffer.append("name=" + name);
        urlBuffer.append("&");
        urlBuffer.append("desc=" + desc);

        log.info("请求的完整url：{}", urlBuffer.toString());

        ResponseEntity<String> forEntity = restTemplate.postForEntity(urlBuffer.toString(), null, String.class);
        // 解析返回结果
        String body = forEntity.getBody();
        JSONObject jsonObject = JSONObject.parseObject(body);
        // 此处转为化为String，是为了避免
        TerminalResponse terminalResponse = jsonObject.getObject("data", TerminalResponse.class);
        terminalResponse.setCarId(desc);

        return ResponseResult.success(terminalResponse);
    }

    public ResponseResult<List<TerminalResponse>> aroundSearch(String center, Integer radius){

        StringBuilder urlBuffer= new StringBuilder();
        urlBuffer.append(AMapConfigConstant.TERMINAL_AROUNDSEARCH);
        urlBuffer.append("?");
        urlBuffer.append("key=" + amapKey);
        urlBuffer.append("&");
        urlBuffer.append("sid=" + amapSid);
        urlBuffer.append("&");
        urlBuffer.append("center=" + center);
        urlBuffer.append("&");
        urlBuffer.append("radius=" + radius);
        log.info("请求的完整url：{}", urlBuffer.toString());

        ResponseEntity<String> forEntity = restTemplate.postForEntity(urlBuffer.toString(), null, String.class);
        // 解析返回结果
        List<TerminalResponse> resultList = new ArrayList<>();
        String body = forEntity.getBody();
        JSONObject jsonObject = JSONObject.parseObject(body);
        JSONObject data = jsonObject.getJSONObject("data");
        JSONArray results = data.getJSONArray("results");
        for (int i = 0; i < results.size(); i++) {
            JSONObject object = results.getJSONObject(i);
            if (Objects.nonNull(object)) {
                String tid = object.getString("tid");
                Long carId = object.getLong("desc");

                TerminalResponse response = new TerminalResponse();
                response.setTid(tid);
                response.setCarId(carId);
                resultList.add(response);
            }
        }


        return ResponseResult.success(resultList);
    }

}
