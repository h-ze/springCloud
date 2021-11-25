package com.feignorder.controller;

import com.feignorder.service.HystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testFeignOrder")
//@DefaultProperties(defaultFallback = "fallbackErrorMessage")

public class TestController {

    @Autowired
    private HystrixService hystrixService;

    @HystrixCommand
    @GetMapping("/getTest")
    public String getTest(){
        return hystrixService.getTest();
    }

    public String fallbackErrorMessage(){
        return "order80服务出现问题,无法使用";
    }
}
