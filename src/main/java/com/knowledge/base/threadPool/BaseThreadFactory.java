package com.knowledge.base.threadPool;

import com.sun.istack.internal.NotNull;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhoulinwen
 * @title: BaseThreadFactory
 * @description: 线程工厂
 * @date 2023/7/26 3:56 PM
 */
@Slf4j
public class BaseThreadFactory implements ThreadFactory {

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    @Override
    public Thread newThread(@NotNull Runnable r) {
        int threadNo = atomicInteger.incrementAndGet();
        log.info("thread no : {}",threadNo);

        return new BaseThread(r,threadNo);
    }
    
}
