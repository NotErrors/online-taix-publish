package com.yuren.internalcommon.response;

import com.yuren.internalcommon.constant.CommonStatusConstant;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/8/26-22:13
 **/

@Data
@Accessors(chain = true)
public class ResponseResult<T> {

    private Integer code;

    private String message;

    private T data;

    public static <T> ResponseResult success() {
        return success(null);
    }

    public static <T> ResponseResult success(T data) {
        return responseResult(CommonStatusConstant.SUCCESS.getCode(), CommonStatusConstant.SUCCESS.getMessage(),
                data);
    }

    public static ResponseResult fail() {
        return fail(CommonStatusConstant.FAIL.getMessage());
    }

    public static ResponseResult fail(String mesage) {
        return fail(mesage, null);
    }

    public static<T> ResponseResult fail(String mesage, T data) {
        return responseResult(CommonStatusConstant.FAIL.getCode(), mesage,
                data);
    }


    public static<T> ResponseResult responseResult(Integer code, String message) {
        return responseResult(code, message, null);
    }


    public static<T> ResponseResult responseResult(Integer code, String message, T data) {
        return new ResponseResult().setCode(code).setMessage(message).setData(data);
    }

}
