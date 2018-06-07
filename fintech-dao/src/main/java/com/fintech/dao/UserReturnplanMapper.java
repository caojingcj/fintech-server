package com.fintech.dao;

import com.fintech.model.UserReturnplan;

public interface UserReturnplanMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserReturnplan record);

    int insertSelective(UserReturnplan record);

    UserReturnplan selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserReturnplan record);

    int updateByPrimaryKey(UserReturnplan record);
}