package com.fintech.dao;

import com.fintech.model.UserBaseinfo;

public interface UserBaseinfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserBaseinfo record);

    int insertSelective(UserBaseinfo record);

    UserBaseinfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserBaseinfo record);

    int updateByPrimaryKey(UserBaseinfo record);
}