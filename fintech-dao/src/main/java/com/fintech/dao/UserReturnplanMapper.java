package com.fintech.dao;

import java.util.List;

import com.fintech.model.UserReturnplan;

public interface UserReturnplanMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserReturnplan record);

    int insertSelective(UserReturnplan record);

    UserReturnplan selectByPrimaryKey(Integer id);

    UserReturnplan selectByOrderPeriod(String orderId, Integer currentPeriod);
    
    List<UserReturnplan> selectOverDueList();

    int updateByPrimaryKeySelective(UserReturnplan record);

    int updateByPrimaryKey(UserReturnplan record);

    int updateCancelByOrderId(String orderId);
}