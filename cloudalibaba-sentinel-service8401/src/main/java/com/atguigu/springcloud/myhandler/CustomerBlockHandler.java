package com.atguigu.springcloud.myhandler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.springcloud.entities.CommenResult;

public class CustomerBlockHandler {
    public static CommenResult handleException(BlockException exception) {
        return new CommenResult(2020, "自定义限流处理信息....CustomerBlockHandler");

    }
}
