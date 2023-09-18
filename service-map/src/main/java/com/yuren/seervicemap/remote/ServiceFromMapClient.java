package com.yuren.seervicemap.remote;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yuren.internalcommon.constant.AMapConfigConstant;
import com.yuren.internalcommon.response.DirectionResponse;
import com.yuren.internalcommon.response.ResponseResult;
import com.yuren.internalcommon.response.ServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/11-23:00
 **/

@Service
@Slf4j
public class ServiceFromMapClient {

    @Value("${amap.key}")
    private String amapKey;

    @Autowired
    private RestTemplate restTemplate;


    public ResponseResult add(String name){

        StringBuilder urlBuffer= new StringBuilder();
        urlBuffer.append(AMapConfigConstant.SERVICE_ADD_URL);
        urlBuffer.append("?");
        urlBuffer.append("key=" + amapKey);
        urlBuffer.append("&");
        urlBuffer.append("name=" + name);
        log.info("请求的完整url：{}", urlBuffer.toString());

        ResponseEntity<String> forEntity = restTemplate.postForEntity(urlBuffer.toString(), null, String.class);
        // 解析返回结果
        String body = forEntity.getBody();
        JSONObject jsonObject = JSONObject.parseObject(body);
        JSONObject data = jsonObject.getJSONObject("data");
        String sid = data.getString("sid");

        ServiceResponse serviceResponse = new ServiceResponse();
        serviceResponse.setSid(sid);

        return ResponseResult.success(serviceResponse);
    }


}
