package com.sentinel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
//sentinel是懒加载 需要访问一次才能出现在控制台
//http://localhost:8080
public class SentinelApplication {
    public static void main(String[] args) {
        SpringApplication.run(SentinelApplication.class,args);
    }
}
