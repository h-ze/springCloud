package com.hystrix.service.impl;

import com.hystrix.service.Client;
import org.springframework.stereotype.Component;


//openFeign的服务熔断
@Component
public class ClientImpl implements Client {
    @Override
    public String getTest() {
        return "熔断器启动";
    }
}
