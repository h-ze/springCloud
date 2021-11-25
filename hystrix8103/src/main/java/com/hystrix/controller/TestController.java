package com.hystrix.controller;

import com.hystrix.service.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private Client client;

    @GetMapping("/getTest")
    public String getTest(){
        return client.getTest();
    }
}
