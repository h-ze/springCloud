package com.sentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.sentinel.config.CustomerBlockHandler;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/testHotKey")
    @SentinelResource(value = "testHotKey",blockHandler = "deal_testHotKey")
    public String testHotKey(@RequestParam(value = "p1",required = false)String p1,
                             @RequestParam(value = "p2",required = false)String p2){
        return "testHotKey";
    }

    public String deal_testHotKey(String p1,String p2, BlockException e){
        return "dealTestHotKey";
    }

    @GetMapping("/testHotKey1/{id}")
    public String testHotKey1(@PathVariable("id")String id){
        return "testHotKey";
    }

    @GetMapping("/testHotKey1")
    @SentinelResource(value = "testHotKey1",blockHandlerClass = CustomerBlockHandler.class,blockHandler = "handler1")
    public String testHotKey1(){
        return "testHotKey1";
    }

}
