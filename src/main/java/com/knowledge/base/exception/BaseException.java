package com.knowledge.base.exception;

import lombok.Getter;

/**
 * @author zhoulinwen
 * @title: BaseException
 * @description: 异常类
 * @date 2023/7/27 9:27 AM
 */

public class BaseException extends RuntimeException{
    @Getter
    private String code;
    @Getter
    private String message;

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BaseException(String message) {
        super(message);
        this.message = message;
    }

    public BaseException(String code, String message,Throwable cause) {
        super(cause);
        this.code = code;
        this.message = message;
    }

    public BaseException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }
    
}
