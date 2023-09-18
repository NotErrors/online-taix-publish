package com.yuren.seervicemap.remote;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yuren.internalcommon.constant.AMapConfigConstant;
import com.yuren.internalcommon.response.DirectionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/4-21:48
 **/
@Service
@Slf4j
public class MapDirectionClient {

    @Value("${amap.key}")
    private String amapKey;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 通过经纬度请求高德api获取起点到终点的距离和时长
     * @param depLongitude
     * @param depLatitude
     * @param destLongitude
     * @param destLatitude
     * @return
     */
    public DirectionResponse direction(String depLongitude,String depLatitude,String destLongitude, String destLatitude){

        StringBuilder urlBuffer= new StringBuilder();
        urlBuffer.append(AMapConfigConstant.DIRECTION_URL);
        urlBuffer.append("?");
        urlBuffer.append("origin=" + depLongitude + "," + depLatitude );
        urlBuffer.append("&");
        urlBuffer.append("destination=" + destLongitude + "," + destLatitude);
        urlBuffer.append("&");
        urlBuffer.append("extensions=basee");
        urlBuffer.append("&");
        urlBuffer.append("output=json");
        urlBuffer.append("&");
        urlBuffer.append("key=" + amapKey);
        log.info("请求的完整url：{}", urlBuffer.toString());

        ResponseEntity<String> directionEntity = restTemplate.getForEntity(urlBuffer.toString(), String.class);
        String directionString = directionEntity.getBody();
        log.info("高德请求后返回的路径规划结果：{}", directionString);

        // 解析返回结果
        DirectionResponse response = parseDirectionString(directionString);

        return response;
    }

    private DirectionResponse parseDirectionString(String directionString) {
        DirectionResponse directionResponse = null;

        try {
            JSONObject jsonObject = JSONObject.parseObject(directionString);
            int status = jsonObject.getIntValue(AMapConfigConstant.STATUS);
            if (status == 1) {
                JSONObject routeObject = jsonObject.getJSONObject(AMapConfigConstant.ROUTE);
                if (routeObject != null) {
                    JSONArray pathArray = routeObject.getJSONArray(AMapConfigConstant.PATH);
                    if (pathArray != null) {
                        JSONObject pathInfoObject = pathArray.getJSONObject(0);
                        directionResponse = new DirectionResponse();
                        int distance = pathInfoObject.getIntValue(AMapConfigConstant.DISTANCE);
                        if (distance != 0) {
                            directionResponse.setDistance(distance);
                        }
                        int duration = pathInfoObject.getIntValue(AMapConfigConstant.DURATION);
                        if (duration != 0) {
                            directionResponse.setDuration(duration);
                        }
                    }
                }
            }
        }catch (Exception e) {
            log.info(e.getMessage());
        }
        return directionResponse;
    }
}
