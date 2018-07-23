package com.fintech.dao;

import java.util.List;
import java.util.Map;

import com.fintech.model.UserReturnplan;
import com.fintech.model.domain.UserReturnplanDo;

public interface UserReturnplanMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserReturnplan record);

    int insertSelective(UserReturnplan record);

    UserReturnplan selectByPrimaryKey(Integer id);
    
    List<UserReturnplanDo> selectByPrimaryKeyList(Map<String, Object>parms);

    UserReturnplan selectByOrderPeriod(String orderId, Integer currentPeriod);
    
    List<UserReturnplan> selectOverDueList();

    int updateByPrimaryKeySelective(UserReturnplan record);

    int updateByPrimaryKey(UserReturnplan record);

    int updateCancelByOrderId(String orderId);
}