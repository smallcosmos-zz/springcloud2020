package com.atguigu.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.myhandler.CustomerBlockHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.atguigu.springcloud.entities.CommenResult;

@RestController
public class RateLimitController {
    @GetMapping("/byResource")
    @SentinelResource(value = "byResource", blockHandler = "handleException")
    public CommenResult byResource() {
        return new CommenResult(200, "按资源名称限流测试OK", new Payment(2020L, "serial001"));
    }

    public CommenResult handleException(BlockException exception) {
        return new CommenResult(444, exception.getClass().getCanonicalName() + "\t 服务不可用");
    }

    @GetMapping("/rateLimit/byUrl")
    @SentinelResource(value = "byUrl")
    public CommenResult byUrl()
    {
        return new CommenResult(200,"urlOK",new Payment(2020L,"serial002"));
    }

    @GetMapping("/rateLimit/customerBlockHandler")
    @SentinelResource(value = "customerBlockHandler",
                    blockHandlerClass = CustomerBlockHandler.class,
                    blockHandler = "handleException")
    public CommenResult customerBlockHandler()
    {
        return new CommenResult(200,"按客戶自定义",new Payment(2020L,"serial003"));
    }





}
