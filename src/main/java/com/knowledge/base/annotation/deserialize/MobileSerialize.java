package com.knowledge.base.annotation.deserialize;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Objects;

/**
 * @author zhoulinwen
 * @title: MobileSerialize
 * @description: 手机号加*，186****6666
 *  使用，在需要加密的手机号字段加上注解  @JsonSerialize(using = MobileSerialize.class)
 * @date 2024/3/21 5:41 PM
 */
@Slf4j
public class MobileSerialize extends JsonSerializer<String> {
    
    @Override
    public void serialize(String mobile, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        try {
            if(StringUtils.isBlank(mobile) || Objects.isNull(serializerProvider)){
                jsonGenerator.writeString("");
            }
            jsonGenerator.writeString(mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));

        }catch (Exception e){
            log.error("",e);
        }

    }
    
}
