package com.fintech.dao;

import java.util.Map;

import com.fintech.model.UserBaseinfo;

public interface UserBaseinfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserBaseinfo record);

    int insertSelective(UserBaseinfo record);

    UserBaseinfo selectByPrimaryKey(String userLoginName);

    int updateByPrimaryKeySelective(UserBaseinfo record);

    int updateByPrimaryKey(UserBaseinfo record);
    
}