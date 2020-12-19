package com.atguigu.springcloud.lb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class MyBalancer implements IBalancer {

    private  AtomicInteger nextServerIndex;

    public MyBalancer(){
        this.nextServerIndex = new AtomicInteger(0);
    }
    @Override
    public ServiceInstance getServiceInstance(List<ServiceInstance> serviceInstances) {
        int index = getNextServerIndex() % serviceInstances.size();
        return serviceInstances.get(index);
    }

    private final int  getNextServerIndex(){
        int current;
        int next;
        do{
            current = nextServerIndex.get();
            next = current >= Integer.MAX_VALUE ? 0 : current + 1;
        }while (!this.nextServerIndex.compareAndSet(current,next));
        log.info("*******第几次访问，次数："+next);
        return next;
    }
}
