package com.hystrix.service.impl;

import com.hystrix.service.TestService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

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
}
