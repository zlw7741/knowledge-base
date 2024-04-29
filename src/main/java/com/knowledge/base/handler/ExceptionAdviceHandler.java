package com.knowledge.base.handler;

import com.knowledge.base.dto.ResultDTO;
import com.knowledge.base.exception.BaseException;
import com.knowledge.base.exception.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Order;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * @author zhoulinwen
 * @title: ExceptionAdviceHandler
 * @description: 异常拦截处理
 * @date 2023/7/27 10:35 AM
 */
@Slf4j
@Order(1)
@RestControllerAdvice
public class ExceptionAdviceHandler implements ResponseBodyAdvice<Object> {

    @ExceptionHandler(Exception.class)
    public ResultDTO ExceptionHandler(Exception e){
        log.error("Unknown Exception",e);
        return ResultDTO.fail(e);
    }

    @ExceptionHandler(BaseException.class)
    public ResultDTO BaseException(BaseException e){
        log.error("BaseException ",e);
        return ResultDTO.fail(e.getCode(),e.getMessage());
    }
//    @ExceptionHandler(UnexpectedRollbackException.class)
//    public ResultDTO ExceptionHandler(UnexpectedRollbackException e){
//        log.error("事务回滚异常",e);
//        return ResultDTO.fail(e);
//    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResultDTO HttpMessageNotReadableException(HttpMessageNotReadableException e){
        log.error("参数类型转换异常，",e);
        return ResultDTO.fail(e);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResultDTO ConstraintViolationException(ConstraintViolationException e){
        log.error("dao validate exception,",e);
        return ResultDTO.fail(e);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultDTO MethodArgumentNotValidException(MethodArgumentNotValidException e){
        BindingResult bindingResult = e.getBindingResult();
        if(bindingResult.hasErrors()){
            StringBuffer sb = new StringBuffer();
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            for (ObjectError allError : allErrors) {
                FieldError fieldError = (FieldError) allError;
                sb.append(fieldError.getField()).append(fieldError.getDefaultMessage()).append(",");
            }
            return ResultDTO.fail(ResultCodeEnum.FAIL.getCode(),sb.substring(0,sb.length()-1));
        }
        return ResultDTO.fail(e);
    }

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        // 如果为不是ResultDTO类型，需要额外处理
        return false;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        return body;
    }
}
