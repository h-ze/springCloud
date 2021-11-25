package com.feignorder.service;

import com.feignorder.service.impl.HystrixServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
//fallback即为服务降级
@FeignClient(value = "HYSTRIX8103",fallback = HystrixServiceImpl.class)
public interface HystrixService {
    @GetMapping("hystrix/getTest")
    String getTest();
}
