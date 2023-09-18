package com.yuren.internalcommon.constant;

import lombok.Data;
import lombok.Getter;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/8/26-22:11
 **/

public enum  CommonStatusConstant {

    /**
     * 验证码1000-1099
     */
    VERIFICATION_CODE_EXPIRE(1099,"验证码已失效"),
    VERIFICATION_CODE_ERROR(1098,"验证码错误"),

    /**
     * token提示 token 1100 - 1199
     */
    TOKEN_CHECK_ERROR(1199, "token解析错误"),

    /**
     * 用户提示： 1200-1299
     */
    USER_ERROR_EXIST(1201, "该用户不存在"),

    /**
     * 计价提示： 1300-1399
     */
    PRICE_RULE_EXIST(1300, "计价规则不存在"),

    PRICE_RULE_EXISTS(1301,"计价规则已存在"),

    PRICE_RULE_NOT_EDIT(1302,"修改数据与当前计价数据一致"),

    PRICE_RULE_CHANGE(1303,"计价规则发生改变"),

    /**
     * 行政地区提示： 1400-1499
     */
    DIC_DISTRICT_EMPTY(1400, "行政地区信息不存在"),

    /**
     * 司机和车辆： 1500-1599
     */
    DRIVER_CAR_BIND_NOT_EXISTS(1500, "司机和车辆绑定关系不存在"),

    DRIVER_NOT_EXISTS(1501, "司机不存在"),

    DRIVER_CAR_BIND_EXISTS(1502, "司机和车辆绑定关系已存在，请勿重复绑定"),

    DRIVER_BIND_EXISTS(1503, "司机已经被绑定了，请勿重复绑定"),

    CAR_BIND_EXISTS(1504,"车辆已经被绑定了，请勿重复绑定"),

    CITY_DRIVER_EMPTY(1505, "当前城市没有可用的司机"),

    /**
     * 订单：1600-1699
     */
    ORDER_GOING_ON(1600, "有订单正在进行中"),
    /**
     * 下单异常
     */
    DEVICE_IS_BLACK(1601, "该设备超过下单次数"),
    /**
     * 地区尚未提供服务
     */
    CITY_SERVICE_NOT_SERVICE(1602, "当前城市不提供叫车服务"),

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
