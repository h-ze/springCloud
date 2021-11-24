package com.openFeign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;


//调用eureka里的实例名即可
@FeignClient(value = "OPENFEIGNPROVIDER",fallback = ClientImpl.class)
@Component
public interface Client {
    @GetMapping("test/getTest1")
    String getTest();
}