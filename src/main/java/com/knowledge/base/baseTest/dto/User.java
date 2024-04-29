package com.knowledge.base.baseTest.dto;

import com.knowledge.base.annotation.group.Group;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author zhoulinwen
 * @title: User
 * @description: 用户
 * @date 2023/7/26 5:58 PM
 */
@Data
public class User {
    @NotBlank
//    @StringEnum(value = {"aa","bb"},message = "类型错误")
    @NotBlank(message = "名称不能为空",groups = {Group.Add.class}) // 给参数指定分组,添加时不能为空
    private String name;
    
    private int age;
}
