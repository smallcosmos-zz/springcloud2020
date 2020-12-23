package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommenResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.lb.IBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@RestController
@Slf4j
public class OrderController {
//    private static final String PAYMENT_URL="http://localhost:8001";
    private static final String PAYMENT_URL="http://CLOUD-PAYMENT-SERVICE";
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private IBalancer balancer;

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/consumer/payment/create")
    public CommenResult<Payment> create(Payment payment){
        return restTemplate.postForObject(PAYMENT_URL+"/payment/create",payment,CommenResult.class);
    }

    @GetMapping("/consumer/payment/postForEntity")
    public CommenResult<Payment> create2(Payment payment){
        return restTemplate.postForEntity(PAYMENT_URL+"/payment/create",payment,CommenResult.class).getBody();
    }

    @GetMapping("/consumer/payment/get/{id}")
    public CommenResult<Payment> getPaymentById(@PathVariable("id") Long id){
        return restTemplate.getForObject(PAYMENT_URL+"/payment/get/"+id,CommenResult.class);
    }

    @GetMapping("/consumer/payment/getForEntity/{id}")
    public CommenResult<Payment> getPaymentById2(@PathVariable("id") Long id){
        ResponseEntity<CommenResult> entity = restTemplate.getForEntity(PAYMENT_URL + "/payment/get/" + id, CommenResult.class);
        if(entity.getStatusCode().is2xxSuccessful()){
            return entity.getBody();
        }else{
            return new CommenResult<>(444,"操作失败");
        }
    }

    @GetMapping("/consumer/payment/lb")
    public String getServerPort(){
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if(instances == null || instances.size() <= 0){
            return null;
        }
        ServiceInstance instance = balancer.getServiceInstance(instances);
        URI uri = instance.getUri();
        return restTemplate.getForObject(uri + "/payment/lb",String.class);
    }

    // ====================> zipkin+sleuth
    @GetMapping("/consumer/payment/zipkin")
    public String paymentZipkin()
    {
        String result = restTemplate.getForObject(PAYMENT_URL+"/payment/zipkin/", String.class);
        return result;
    }


}
