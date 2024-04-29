package com.knowledge.base.threadPool;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author zhoulinwen
 * @title: BaseExecutorService
 * @description: 线程池创建（工厂方式）
 * @date 2023/7/26 3:52 PM
 */
@Configuration
public class BaseExecutorService {
    
    @Bean
    public ExecutorService getExecutorService(){
        return new ThreadPoolExecutor(5,10,60, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),new BaseThreadFactory());
    }
    
}
