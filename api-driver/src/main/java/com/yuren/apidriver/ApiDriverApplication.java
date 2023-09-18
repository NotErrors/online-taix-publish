package com.yuren.apidriver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/9-10:42
 **/
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class ApiDriverApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiDriverApplication.class);
    }

}
