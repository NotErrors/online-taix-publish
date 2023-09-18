package com.yuren.internalcommon.request;

import com.sun.istack.internal.NotNull;
import com.yuren.internalcommon.dto.PointDTO;
import lombok.Data;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/13-21:18
 **/
@Data
public class ApiDriverPointRequest {

    @NotNull
    private Long carId;

    @NotNull
    private PointDTO[] points;
}
