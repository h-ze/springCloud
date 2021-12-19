package com.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ReceiveApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReceiveApplication.class,args);
    }
}
