package com.knowledge.base.annotation.group;

/**
 * @author zhoulinwen
 * @title: Group
 * @description: 新增和更新接口可能用的同一个DTO，在请求时，参数在新增时需要校验，修改时不需要校验，使用参数校验分组功能
 * @date 2023/7/27 11:32 AM
 */
public interface Group {
    
    interface Add{}
    
    interface Update{}
    
}
