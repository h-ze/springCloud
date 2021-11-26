package com.feignorder.service.impl;

import com.feignorder.service.HystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

@Service
public class HystrixServiceImpl implements HystrixService {
    @Override
    public String getTest() {
        return "降级了";
    }


}
