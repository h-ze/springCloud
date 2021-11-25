package com.openFeign.controller;


import com.openFeign.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/getTest1")
    public String getTest(){
        return testService.getTrueMessage();
    }

    @GetMapping("/getTest2")
    public String getTest2(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return testService.getErrorMessage();
    }
}
