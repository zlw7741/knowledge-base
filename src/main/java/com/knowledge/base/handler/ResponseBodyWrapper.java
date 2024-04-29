package com.knowledge.base.handler;

import com.knowledge.base.dto.ResultDTO;
import com.knowledge.base.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author zhoulinwen
 * @title: ResponseBodyWrapper
 * @description: 统一返回处理
 * @date 2023/7/27 11:51 AM
 */
@Slf4j
@ControllerAdvice
public class ResponseBodyWrapper implements ResponseBodyAdvice<Object> {

    private final ResponseBodyWrapperProperties responseBodyWrapperProperties;

    public ResponseBodyWrapper(ResponseBodyWrapperProperties responseBodyWrapperProperties) {
        this.responseBodyWrapperProperties = responseBodyWrapperProperties;
    }

    /**
     * 是否对返回处理
     * 返回true需要执行beforeBodyWrite方法
     * 返回false不会执行beforeBodyWrite方法
     * @param returnType
     * @param converterType
     * @return
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return !returnType.getParameterType().equals(ResultDTO.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if(body instanceof ResultDTO){
            return body;
        }
        if(String.class.equals(returnType.getParameterType())){
            // 字符要转成json，不然会报错
            return JsonUtils.object2Json(ResultDTO.success((body)));
        }
        // 如果配置了不需要包装的接口前缀
        String[] prefixUrls = responseBodyWrapperProperties.getPrefixUrls();
        if(prefixUrls != null){
            for (String prefixUrl : prefixUrls) {
                // url中在豁免前缀
                if(StringUtils.startsWith(
                        ((ServletServerHttpRequest)request).getServletRequest().getRequestURI(),prefixUrl)){
                    return body;
                }
            }
        }
        return ResultDTO.success(body);
    }
}
