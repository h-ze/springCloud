package com.sentinel.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("flowLimit")
public class FlowLimitController {
    @GetMapping("/testA")
    public String getTestA(){
        /*try {
            TimeUnit.MILLISECONDS.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        return "test A";
    }

    @GetMapping("/testB")
    public String getTestB(){
        return "test B";
    }
}
