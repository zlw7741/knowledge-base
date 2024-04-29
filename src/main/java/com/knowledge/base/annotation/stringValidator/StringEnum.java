package com.knowledge.base.annotation.stringValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhoulinwen
 * @title: StringEnum
 * @description: 字符枚举校验
 * @date 2023/7/26 5:37 PM
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.PARAMETER,ElementType.METHOD})
@Constraint(validatedBy = {StringEnumValidator.class})
public @interface StringEnum {
    
    String message() default "字符校验不通过";
    
    Class<?>[] groups() default {};
    
    String[] value() default {};
    
    Class<? extends Payload>[] payload() default {};
    
}
