package com.hystrix.controller;

import com.hystrix.service.impl.HystrixServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hystrix")
public class HystrixController {

    @Autowired
    private HystrixServiceImpl testService;

    //服务降级
    @GetMapping("/getTest")
    public String getTest(){
        return testService.getTrueMessage();
    }

    //服务熔断
    @GetMapping("/circuitBreaker/{id}")
    public String getCircuitBreaker(@PathVariable("id") Integer id){
        return testService.circuitBreaker(id);
    }
}
