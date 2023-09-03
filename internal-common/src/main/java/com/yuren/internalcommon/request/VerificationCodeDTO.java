package com.yuren.internalcommon.request;

import lombok.Data;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/8/20-22:57
 **/
@Data
public class VerificationCodeDTO {

    private String passengerPhone;

    private String verificationCode;
}
