package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommenResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class PaymentController {
    @Autowired
    PaymentService paymentService;
    @Value("${server.port}")
    private String serverPort;

    @PostMapping("payment/create")
    public CommenResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        if(result > 0){
            return new CommenResult(200,"插入数据库成功,serverPort："+serverPort,result);
        }else{
            return new CommenResult(444,"插入数据库失败",null);
        }
    }

    @GetMapping("/payment/get/{id}")
    public CommenResult<Payment> getPaymentById(@PathVariable("id") Long id){
        Payment payment = paymentService.getPaymentById(id);
        if(payment != null){
            return new CommenResult(200,"查询成功，serverPort："+serverPort,payment);
        }else{
            return new CommenResult(444,"查询结果为空，查询ID为"+id,null);
        }
    }

    @GetMapping("/payment/lb")
    public String getServerPort(){
        return serverPort;
    }
}
