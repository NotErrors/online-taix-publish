package com.yuren.seervicemap.controller;

import com.yuren.internalcommon.response.ResponseResult;
import com.yuren.seervicemap.service.DicDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/6-21:46
 **/
@RestController
public class DicDistrictController {

    @Autowired
    private DicDistrictService dicDistrictService;

    @GetMapping("/dic-distrcit")
    public ResponseResult initDicDistrict(String keyword) {

        dicDistrictService.initDicDistrict(keyword);

        return ResponseResult.success();
    }
}
