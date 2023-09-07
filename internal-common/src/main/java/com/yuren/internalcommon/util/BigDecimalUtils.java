package com.yuren.internalcommon.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/5-22:50
 **/
public class BigDecimalUtils {

    /**
     * 加法
     * @param v1
     * @param v2
     * @return
     */
    public static double add(double v1, double v2){
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);

        return b1.add(b2).doubleValue();
    }

    /**
     * 减法
     * @param v1
     * @param v2
     * @return
     */
    public static double subtract(double v1, double v2){
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);

        return b1.subtract(b2).doubleValue();
    }

    /**
     * 乘法
     * @param v1
     * @param v2
     * @return
     */
    public static double multiply(double v1, double v2){
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);

        return b1.multiply(b2).doubleValue();
    }

    /**
     * 除法
     * @param v1
     * @param v2
     * @return
     */
    public static double divide(double v1, double v2){
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);

        return b1.divide(b2, 2, RoundingMode.HALF_UP).doubleValue();
    }

}
