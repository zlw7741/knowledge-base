package com.knowledge.base.exception;

/**
 * @author zhoulinwen
 * @title: BusinessException
 * @description: 业务异常
 * @date 2023/7/27 10:31 AM
 */
public class BusinessException extends BaseException{


    public BusinessException(String code, String message) {
        super(code, message);
    }

    public BusinessException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum);
    }
}
