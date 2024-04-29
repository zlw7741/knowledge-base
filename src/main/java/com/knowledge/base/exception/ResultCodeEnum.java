package com.knowledge.base.exception;


/**
 * @author zhoulinwen
 * @title: ResultCodeEnum
 * @description: 异常码
 * @date 2023/7/27 10:25 AM
 */
public enum ResultCodeEnum {
    
    SUCCESS("1000","成功"),
    FAIL("9999","失败"),
    FORMAT_ERROR("10001","格式化错误"),
    NOT_SUPPER_ERROR("10002","不支持该接口"),
    ;
    private String code;
    private String message;

    ResultCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
