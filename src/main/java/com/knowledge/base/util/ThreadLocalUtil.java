package com.knowledge.base.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhoulinwen
 * @title: ThreadLocalUtil
 * @description: 本地线程工具
 * @date 2024/2/23 2:23 PM
 */
public class ThreadLocalUtil {
    
    public static ThreadLocal<Map<String,Object>> THREAD_LOCAL = ThreadLocal.withInitial(()->new ConcurrentHashMap<>(8));
    
    public static <T> T get(String key){
        if(StringUtils.isBlank(key)){
            return null;
        }
        Map<String, Object> map = THREAD_LOCAL.get();
        return Objects.isNull(map) ? null: (T) map.get(key);
    }
    
    public static void put(String key,Object value){
        if(StringUtils.isBlank(key) || Objects.isNull(value)){
            return;
        }
        Map<String, Object> map = THREAD_LOCAL.get();
        map.put(key,value);
    }
    
    public static void remove(String key){
        if(StringUtils.isBlank(key)){
            return;
        }
        Map<String, Object> map = THREAD_LOCAL.get();
        map.remove(key);
    }

}
