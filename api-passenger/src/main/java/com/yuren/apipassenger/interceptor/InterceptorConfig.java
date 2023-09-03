package com.yuren.apipassenger.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/3-10:36
 **/
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Bean
    public JwtInterceptor jwtInterceptor(){
        return new JwtInterceptor();
    }
    /**
     * 注册自定义interceptor
     * 若拦截路径多，则拦截路径填写拦截全部，然后在逐个排除不拦截的，反之也如此
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                // 设置拦截路径
                .addPathPatterns("/**")
                // 设置不拦截路径
                .excludePathPatterns("/verification-code")
                .excludePathPatterns("/verification-code-check/")
                .excludePathPatterns("/refresh-token")
        ;
    }
}
