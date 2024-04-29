package com.knowledge.base.handler;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhoulinwen
 * @title: ResponseBodyWrapperProperties
 * @description: 返回url过滤配置
 * @date 2023/7/27 2:19 PM
 */
@Configuration
@ConfigurationProperties("base.response.wrapper")
public class ResponseBodyWrapperProperties {
    @Getter
    @Setter
    private String[] prefixUrls;
    
}
