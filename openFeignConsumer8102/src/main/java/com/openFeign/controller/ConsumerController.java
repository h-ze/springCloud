package com.openFeign.controller;


import com.openFeign.service.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class ConsumerController {

    @Autowired
    private Client client;

    @GetMapping("/getConsumer")
    public String getConsumer(){
        return client.getTest();
    }
}
