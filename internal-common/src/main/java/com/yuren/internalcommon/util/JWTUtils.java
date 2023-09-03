package com.yuren.internalcommon.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.yuren.internalcommon.constant.IdentityConstant;
import com.yuren.internalcommon.dto.TokenResult;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/1-22:05
 **/
public class JWTUtils {

    // 盐值
    private static final String SIGN = "awsdfjko;1234^&*";

    private static final String JWT_KEY_PHONE = "phone";

    private static final String JWT_KEY_IDENTITY = "identity";

    private static final String JWT_TOKEN_TYPE = "tokenType";

    private static final String JWT_TOKEN_TIME = "tokenTime";

    // 生成JWTtoken
    public static String generatorToken(String passengerPhone, String idntity, String tokenType) {
        Map<String, String> map = new HashMap<>();
        map.put(JWT_KEY_PHONE, passengerPhone);
        map.put(JWT_KEY_IDENTITY, idntity);
        map.put(JWT_TOKEN_TYPE, tokenType);
        map.put(JWT_TOKEN_TIME, Calendar.getInstance().getTime().toString());


        // 封装map
        JWTCreator.Builder builder = JWT.create();
        map.forEach((k, v) -> {
            builder.withClaim(k, v);
        });


        // 设置盐值
        String sign = builder.sign(Algorithm.HMAC256(SIGN));
        return sign;
    }

    public static TokenResult checkToken(String token) {
        TokenResult tokenResult = null;
        try {
            tokenResult = JWTUtils.parseToken(token);
        } catch (Exception e) {

        }
        return tokenResult;
    }



    // 解析JWTToken
    public static TokenResult parseToken(String token) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
        String phone = decodedJWT.getClaim(JWT_KEY_PHONE).asString();
        String identity = decodedJWT.getClaim(JWT_KEY_IDENTITY).asString();
        String tokenType = decodedJWT.getClaim(JWT_TOKEN_TYPE).asString();

        TokenResult tokenResult = new TokenResult();
        tokenResult.setPhone(phone);
        tokenResult.setIdentity(identity);
        tokenResult.setTokenType(tokenType);
        return tokenResult;
    }


    public static void main(String[] args) {
//        String token = generatorToken("19924289940", IdentityConstant.IDENTITY_PASSENGER);
//        System.out.println("JWT_Token：" + token);
//
//        System.out.println("解析后的手机号码：" + parseToken(token).getPhone());
//        System.out.println("解析后的身份标识：" + parseToken(token).getIdentity());
    }
}
