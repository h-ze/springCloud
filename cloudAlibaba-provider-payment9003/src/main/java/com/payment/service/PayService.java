package com.payment.service;


import com.common.entity.Payment;

public interface PayService {
    Payment getPaymentById(String id);
}
