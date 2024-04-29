package com.knowledge.base.config;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author zhoulinwen
 * @title: EnvBeanUtil
 * @description: 环境变量工具
 * @date 2024/3/15 3:52 PM
 */
@Component
public class EnvBeanUtil implements EnvironmentAware {
    
    private static Environment env;


    @Override
    public void setEnvironment(Environment environment) {
        this.env = environment;
    }
    
    public static String getString(String key){
        return env.getProperty(key);
    }
    
}
