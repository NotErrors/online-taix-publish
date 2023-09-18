package com.yuren.apiboss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/9-9:54
 **/
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ApiBossApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiBossApplication.class);
    }
}
