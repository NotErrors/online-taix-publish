package com.yuren.seervicemap.service;

import com.alibaba.fastjson.JSONObject;
import com.yuren.internalcommon.constant.AMapConfigConstant;
import com.yuren.internalcommon.constant.CommonStatusConstant;
import com.yuren.internalcommon.dto.DicDistrict;
import com.yuren.internalcommon.dto.DicDistrictInfo;
import com.yuren.internalcommon.response.ResponseResult;
import com.yuren.seervicemap.mapper.DicDistrictMapper;
import com.yuren.seervicemap.remote.MapDicDistrictClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/6-21:46
 **/
@Service
@Slf4j
public class DicDistrictService {


    @Autowired
    private MapDicDistrictClient mapDicDistrictClient;

    @Autowired
    private DicDistrictMapper dicDistrictMapper;

    public ResponseResult initDicDistrict(String keyword) {

        // 获取请求行政地区的信息
        String dicDistrictResult = mapDicDistrictClient.dicDistrict(keyword);

        // 解析结果
        JSONObject disDistrictJSONObject = JSONObject.parseObject(dicDistrictResult);
        int status = disDistrictJSONObject.getIntValue(AMapConfigConstant.STATUS);
        if (status == 1) {
            DicDistrictInfo districtInfo = disDistrictJSONObject.getObject(AMapConfigConstant.DISTRICTS, DicDistrictInfo.class);

            // 存储到数据库中
            if (districtInfo != null) {
                insertDicDistrict(districtInfo, "0");
            }
            return ResponseResult.success();

        }
        return ResponseResult.fail(CommonStatusConstant.DIC_DISTRICT_EMPTY.getCode(), CommonStatusConstant.DIC_DISTRICT_EMPTY.getMessage());
    }

    private void insertDicDistrict(DicDistrictInfo districtInfo, String parentAddressCode) {
        DicDistrict dicDistrict = new DicDistrict();
        dicDistrict.setAddressCode(districtInfo.getAdcode());
        dicDistrict.setAddressName(dicDistrict.getAddressName());
        dicDistrict.setLevel(generatorLevel(districtInfo.getLevel()));
        dicDistrict.setParentAddressCode(parentAddressCode);

        dicDistrictMapper.insert(dicDistrict);

        List<DicDistrictInfo> districts = districtInfo.getDistricts();
        if (districts != null && !districts.isEmpty()) {
            for (DicDistrictInfo dicDistrictInfo : districts) {
                insertDicDistrict(dicDistrictInfo, dicDistrict.getAddressCode());
            }
        }
    }

    private int generatorLevel(String level) {
        int intLevel = 0 ;
        if (level.trim().equals("country")) {
            intLevel = 0;
        } else if (level.trim().equals("province")) {
            intLevel = 1;
        } else if (level.trim().equals("city")) {
            intLevel = 2;
        } else if (level.trim().equals("district")) {
            intLevel = 3;
        }
        return intLevel;
    }
}
