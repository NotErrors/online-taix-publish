package com.yuren.internalcommon.util;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/3-11:28
 **/
public class RedisPrefixUtils {


    private static String verificationcodeRedisPre = "passenger-verification-code-";

    private static String tokenRedisPre = "token-";


    public static String generatorTokenKey(String passengerPhone, String identity, String tokenType) {
        return tokenRedisPre + passengerPhone + "-" + identity + "-" + tokenType;
    }

    public static String generatorKeyByPhone(String passengerPhone) {
        return verificationcodeRedisPre + passengerPhone ;
    }



}
