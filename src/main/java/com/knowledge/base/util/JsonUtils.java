package com.knowledge.base.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.knowledge.base.exception.BaseException;
import com.knowledge.base.exception.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;

/**
 * @author zhoulinwen
 * @title: JsonUtils
 * @description: json工具
 * @date 2023/7/31 2:06 PM
 */
@Slf4j
public class JsonUtils {
    
    public static ObjectMapper objectMapper = new ObjectMapper();
    
    static {
        // 转换为json格式
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        // 如果json中有新增的字段，并且实体类中不存在，不报错
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        // 修改日期格式
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }
    
    public static String object2Json(Object obj){
        String jsonStr = null;
        try {
            jsonStr = objectMapper.writeValueAsString(obj);
        }catch (JsonProcessingException e){
            log.error("",e);
            throw new BaseException(ResultCodeEnum.FORMAT_ERROR);
        }
        return jsonStr;
    }
    
    
}
