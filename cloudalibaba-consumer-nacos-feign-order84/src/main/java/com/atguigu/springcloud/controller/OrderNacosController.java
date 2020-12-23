package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderNacosController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping(value = "/consumer/payment/nacos/{id}")
    public String getPayment(@PathVariable("id") Integer id){
        return paymentService.getPayment(id);
    }
}
