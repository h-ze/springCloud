package com.payment.controller;

import com.payment.service.PayService;
import entitiy.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PayService payService;

    @GetMapping("/getPayment")
    public Payment getPayment(String id){
        Payment paymentById = payService.getPaymentById(id);
        return paymentById;
    }

    @GetMapping("/getPaymentById/{id}")
    public String getPaymentById(@PathVariable String id){
        return "获取支付信息，id为："+id;
    }

}
