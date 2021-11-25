package com.openFeign.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.openFeign.service.TestService;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

    @HystrixCommand(fallbackMethod = "fallbackErrorMessage",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")
    })
    @Override
    public String getTrueMessage() {
        return "测试openFeign连接成功";
    }


    @Override
    public String getErrorMessage() {
        return "测试openFeign连接失败";
    }

    public String fallbackErrorMessage(){
        return "fallback调试";
    }
}
