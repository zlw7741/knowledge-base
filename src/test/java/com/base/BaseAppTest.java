package com.base;

import com.knowledge.base.BaseApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutorService;

/**
 * @author zhoulinwen
 * @title: BaseAppTest
 * @description: 测试
 * @date 2023/7/26 4:19 PM
 */
@Slf4j
@SpringBootTest(classes = BaseApplication.class)
public class BaseAppTest {
    
    @Autowired
    ExecutorService executorService;
    @Test
    public void threadPoolTest(){
        log.error("开始======================");
        for (int i = 0; i < 10; i++) {
            executorService.execute(()->{});
        }
        
    }
    
    @Test
    public void test(){
        System.out.println("aaaaaaaaaaaaaaaaaaaaaa");
    }
    
}

    
