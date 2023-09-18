package com.yuren.internalcommon.constant;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/9-15:14
 **/
public class DriverCarConstants {

    /**
     * 司机与车辆绑定关系 1-绑定
     */
    public static final int DRIVER_CAR_BIND = 1;

    /**
     * 司机与车辆绑定关系 2-解绑
     */
    public static final int DRIVER_CAR_UNBIND = 2;

    /**
     * 司机状态： 0-有效
     */
    public static final int DRIVER_STATE_VALID = 0;

    /**
     * 司机状态： 1-无效
     */
    public static final int DRIVER_STATE_UNVALID = 1;

    /**
     * 司机存在
     */
    public static  final int DRIVER_EXISTS = 1;

    /**
     * 司机不存在
     */
    public static final int DRIVER_NOT_EXISTS = 0;


    /**
     * 收车
     */
    public static final int DRIVER_WORK_STATUS_STOP = 0;

    /**
     * 出车
     */
    public static final int DRIVER_WORK_STATUS_START = 1;

    /**
     * 暂停
     */
    public static final int DRIVER_WORK_STATUS_SUSPEND = 2;

}
