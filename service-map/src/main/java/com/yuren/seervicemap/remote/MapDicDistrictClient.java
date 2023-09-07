package com.yuren.seervicemap.remote;

import com.yuren.internalcommon.constant.AMapConfigConstant;
import com.yuren.internalcommon.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/6-21:57
 **/
@Service
@Slf4j
public class MapDicDistrictClient {


    @Value("${amap.key}")
    private String amapKey;

    @Autowired
    private RestTemplate restTemplate;

    public String dicDistrict(String keyword) {

        // 拼接请求地区url
        StringBuilder url = new StringBuilder();
        url.append(AMapConfigConstant.DISTRICT_URL);
        url.append("?");
        url.append("keyword=" + keyword);
        url.append("&");
        url.append("subdistrict=3");
        url.append("&");
        url.append("key=" + amapKey);
        log.info("请求行政区域的url路径：{}", url.toString());

        // 发送请求
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url.toString(), String.class);

        return forEntity.getBody();
    }

}
