package com.fintech.dao;

import com.fintech.model.UserContract;

public interface UserContractMapper {
    int deleteByPrimaryKey(String contractId);

    int insert(UserContract record);

    int insertSelective(UserContract record);

    UserContract selectByPrimaryKey(String contractId);

    UserContract selectByOrderId(String orderId);

    int updateByPrimaryKeySelective(UserContract record);

    int updateByPrimaryKey(UserContract record);
}