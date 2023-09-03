package com.yuren.serviceprice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * author:拉丁
 * email:1228316356@qq.com
 * createTime:2023/9/3-22:45
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class ServicePriceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServicePriceApplication.class);
    }
}
