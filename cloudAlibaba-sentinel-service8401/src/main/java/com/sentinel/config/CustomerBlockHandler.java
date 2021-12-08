package com.sentinel.config;


import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.context.annotation.Configuration;

public class CustomerBlockHandler {
    public static String handler1(BlockException exception){
        return "handler1";
    }

    public static String handler2(BlockException exception){
        return "handler2";
    }
}
