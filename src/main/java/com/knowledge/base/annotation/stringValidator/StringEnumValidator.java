package com.knowledge.base.annotation.stringValidator;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author zhoulinwen
 * @title: StringEnumValidator
 * @description: 字符枚举校验
 * @date 2023/7/26 5:46 PM
 */
public class StringEnumValidator implements ConstraintValidator<StringEnum,String> {

    private Set<String> values;
    
    @Override
    public void initialize(StringEnum constraintAnnotation) {
        values = new HashSet<>(Arrays.asList(constraintAnnotation.value()));
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if(value == null){
            return true;
        }
        return values.contains(value);
    }
}
