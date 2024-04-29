package com.knowledge.base.threadPool;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhoulinwen
 * @title: BaseThread
 * @description: 线程创建
 * @date 2023/7/26 4:01 PM
 */
@Slf4j
public class BaseThread extends Thread{

    private Runnable target;
    
    private int threadNo;

    public BaseThread(Runnable target, int threadNo) {
        super("Thread:"+threadNo);
        this.target = target;
        this.threadNo = threadNo;
    }

    @Override
    public void run() {
        log.info("Runnable task start >>> threadNo:{},threadInfo:{}",threadNo, Thread.currentThread());
        super.run();
        log.info("Runnable task end >>> threadNo:{},threadInfo:{}",threadNo, Thread.currentThread());
    }
}
