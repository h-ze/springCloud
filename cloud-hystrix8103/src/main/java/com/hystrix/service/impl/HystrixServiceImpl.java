package com.hystrix.service.impl;

import com.hystrix.service.HystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class HystrixServiceImpl implements HystrixService {

    //最初的降级处理
    @HystrixCommand(fallbackMethod = "fallbackErrorMessage",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")
    })
    @Override
    public String getTrueMessage() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "测试openFeign连接成功";
    }


    @Override
    public String getErrorMessage() {
        return "测试openFeign连接失败";
    }

    public String fallbackErrorMessage(){
        return "超时,fallback回调，8103系统问题";
    }

    //=====服务熔断
    @HystrixCommand(fallbackMethod = "circuitBreaker_fallback",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),// 是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),// 请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"), // 时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"),// 失败率达到多少后跳闸
    })
    public String circuitBreaker(@PathVariable("id") Integer id){
        if(id < 0) {
            throw new RuntimeException("******id 不能负数");
        }
        return "服务熔断相关内容调用";
    }

    public String circuitBreaker_fallback(@PathVariable("id") Integer id){
        return "服务熔断fallback调用";
    }
}
