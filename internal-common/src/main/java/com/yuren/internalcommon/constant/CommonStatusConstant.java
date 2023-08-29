package com.yuren.internalcommon.constant;

import lombok.Data;
import lombok.Getter;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/8/26-22:11
 **/

public enum  CommonStatusConstant {

    VERIFICATION_CODE_EXPIRE(1099,"验证码已失效"),
    VERIFICATION_CODE_ERROR(1098,"验证码错误"),

    SUCCESS(1,"success"),
    FAIL(2,"fail");

    @Getter
    private int code;
    @Getter
    private String message;

    CommonStatusConstant(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
