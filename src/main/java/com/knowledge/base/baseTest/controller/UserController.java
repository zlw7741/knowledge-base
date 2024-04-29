package com.knowledge.base.baseTest.controller;

import com.knowledge.base.annotation.group.Group;
import com.knowledge.base.baseTest.dto.User;
import com.knowledge.base.dto.ResultDTO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author zhoulinwen
 * @title: UserController
 * @description: 自定义注解测试
 * @date 2023/7/26 5:56 PM
 */
@RestController
@RequestMapping("/user")
public class UserController {
    
    @PostMapping("/addUser")
    public User addUser(@RequestBody @Validated(Group.Add.class) User user){ // 指定分组可用,添加时不能为空
        return user;
    }

    @PostMapping("/updateUser")
    public ResultDTO<String> updateUser(@RequestBody @Validated(Group.Update.class) User user){ // 指定分组可用，更新时可为空
        return ResultDTO.success(user.toString());
    }

    @PostMapping("/test/delUser")
    public Object delUser(@RequestBody @Validated User user){ // 指定分组可用，更新时可为空
        return user;
    }

}
