package com.yuren.internalcommon.request;

import com.yuren.internalcommon.dto.PointDTO;
import lombok.Data;

import java.util.List;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/12-23:03
 **/
@Data
public class PointRequest {

    private String tid;

    private String trid;

    private List<PointDTO> points;

}
