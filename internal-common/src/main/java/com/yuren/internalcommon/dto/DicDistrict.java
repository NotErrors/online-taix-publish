package com.yuren.internalcommon.dto;

import lombok.Data;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/6-21:33
 **/
@Data
public class DicDistrict {

    private String addressCode;

    private String addressName;

    private String parentAddressCode;

    private Integer level;
}
