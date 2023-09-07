package com.yuren.internalcommon.dto;

import lombok.Data;

import java.util.List;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/6-22:58
 **/
@Data
public class DicDistrictInfo {

    private String citycode;

    private String adcode;

    private String name;

    private String center;

    private String level;

    private List<DicDistrictInfo> districts;
}
