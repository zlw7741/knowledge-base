package com.knowledge.base.dto;

import com.knowledge.base.exception.ResultCodeEnum;
import lombok.Data;

/**
 * @author zhoulinwen
 * @title: ResultDTO
 * @description: 统一返回
 * @date 2023/7/27 10:37 AM
 */
@Data
public class ResultDTO<T> {

    private String code;
    
    private String message;
    
    private T data;

    public ResultDTO(String code, String message) {
        this.code = code;
        this.message = message;
    }
    public ResultDTO(ResultCodeEnum resultCodeEnum) {
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }

    public ResultDTO() {
        this.code = ResultCodeEnum.SUCCESS.getCode();
        this.message = ResultCodeEnum.SUCCESS.getMessage();
    }

    public ResultDTO(T data) {
        this.code = ResultCodeEnum.SUCCESS.getCode();
        this.message = ResultCodeEnum.SUCCESS.getMessage();
        this.data = data;
    }
    public static ResultDTO success(){
        return new ResultDTO();
    }
    public static ResultDTO success(Object data){
        return new ResultDTO(data);
    }
    
    public static ResultDTO fail(){
        return new ResultDTO(ResultCodeEnum.FAIL.getCode(),ResultCodeEnum.FAIL.getMessage());
    }
    public static ResultDTO fail(Throwable e){
        return new ResultDTO(ResultCodeEnum.FAIL.getCode(),e.getMessage());
    }
    public static ResultDTO fail(String code,String message){
        return new ResultDTO(code,message);
    }
    public static ResultDTO fail(ResultCodeEnum resultCodeEnum){
        return new ResultDTO(resultCodeEnum.getCode(),resultCodeEnum.getMessage());
    }
    
    
}
