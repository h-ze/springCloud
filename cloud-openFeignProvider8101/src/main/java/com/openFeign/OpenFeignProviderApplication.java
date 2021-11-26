package com.openFeign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
//@EnableHystrix已经包含@EnableCircuitBreaker的内容
@EnableHystrix
public class OpenFeignProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OpenFeignProviderApplication.class,args);
    }
}
