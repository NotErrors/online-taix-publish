package com.yuren.apidriver.interceptor;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.yuren.internalcommon.constant.TokenConfigConstant;
import com.yuren.internalcommon.dto.TokenResult;
import com.yuren.internalcommon.response.ResponseResult;
import com.yuren.internalcommon.util.JWTUtils;
import com.yuren.internalcommon.util.RedisPrefixUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/3-10:23
 **/
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean result = true;
        String resultString = null;

        String token = request.getHeader("Authorization");
        TokenResult tokenResult = JWTUtils.checkToken(token);

        if (Objects.isNull(tokenResult)) {
            result = false;
            resultString = "token invalid";
        } else {
            String phone = tokenResult.getPhone();
            String identity = tokenResult.getIdentity();
            String tokenKey = RedisPrefixUtils.generatorTokenKey(phone, identity, TokenConfigConstant.ACCESS_TOKEN_KEY);
            String jwtToken = stringRedisTemplate.opsForValue().get(tokenKey);
            if (StringUtils.isBlank(jwtToken) || !jwtToken.trim().equals(token.trim())) {
                result = false;
                resultString = "token invalid";
            }
        }

        if (!result) {
            PrintWriter writer = response.getWriter();
            writer.println(JSONObject.toJSON(ResponseResult.fail(resultString)));
        }

        return result;
    }
}
