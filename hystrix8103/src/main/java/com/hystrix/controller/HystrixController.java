package com.hystrix.controller;

import com.hystrix.service.TestService;
import com.hystrix.service.impl.TestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hystrix")
public class HystrixController {
    @Autowired
    //private TestService testService;
    private TestServiceImpl testService;

    @GetMapping("/getTest")
    public String getTest(){
        return testService.getTrueMessage();
    }
}
