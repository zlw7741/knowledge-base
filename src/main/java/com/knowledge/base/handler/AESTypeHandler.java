package com.knowledge.base.handler;

import com.knowledge.base.util.AESUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author zhoulinwen
 * @title: AESTypeHandler
 * @description: AES加解密处理器，用于mybatis存库时自动加解密
 * @date 2024/3/15 3:34 PM
 * 
 * 使用方法：
 * 在mybatis的xml中设置typeHandler字段
 * 1.在需要解密的result标签中加入：typeHandler="com.knowledge.base.handler.AESTypeHandler"
 * 2.在需要插入的字段中加入：typeHandler=com.knowledge.base.handler.AESTypeHandler  例如：#{userId,typeHandler=com.knowledge.base.handler.AESTypeHandler}
 */
@Slf4j
public class AESTypeHandler extends BaseTypeHandler<Object> {


    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        try {
            if(StringUtils.isNotBlank((String)parameter)){
                ps.setString(i, AESUtil.ecbEncrypt((String)parameter));
            }else {
                ps.setString(i, (String)parameter);
            }
        }catch (Exception e){
            log.info("字段加密错误",e);
            throw new SQLException("字段加密错误",e);
        }
    }

    @Override
    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
        try {
            String columnNameValue = rs.getString(columnName);
            if (StringUtils.isNotBlank(columnNameValue)) {
                return AESUtil.ecbDecrypt(columnNameValue);
            } else {
                return columnNameValue;
            }
        } catch (Exception e) {
            log.info("字段解密错误", e);
            throw new SQLException("字段解密错误", e);
        }
    }

    @Override
    public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        try {
            String columnNameValue = rs.getString(columnIndex);
            if (StringUtils.isNotBlank(columnNameValue)) {
                return AESUtil.ecbDecrypt(columnNameValue);
            } else {
                return columnNameValue;
            }
        } catch (Exception e) {
            log.info("字段解密错误", e);
            throw new SQLException("字段解密错误", e);
        }
    }

    @Override
    public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        try {
            String columnNameValue = cs.getString(columnIndex);
            if (StringUtils.isNotBlank(columnNameValue)) {
                return AESUtil.ecbDecrypt(columnNameValue);
            } else {
                return columnNameValue;
            }
        } catch (Exception e) {
            log.info("字段解密错误", e);
            throw new SQLException("字段解密错误", e);
        }
    }
}
