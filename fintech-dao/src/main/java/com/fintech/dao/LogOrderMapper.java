package com.fintech.dao;

import com.fintech.model.LogOrder;

public interface LogOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LogOrder record);

    int insertSelective(LogOrder record);

    LogOrder selectByPrimaryKey(Integer id);
    
    LogOrder selectByPrimaryKeyStatus(String orderId);

    int updateByPrimaryKeySelective(LogOrder record);

    int updateByPrimaryKey(LogOrder record);
}