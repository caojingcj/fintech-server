package com.fintech.dao;

import java.util.Map;

import com.fintech.model.OrderBaseinfo;

public interface OrderBaseinfoMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(OrderBaseinfo record);

    int insertSelective(OrderBaseinfo record);

    OrderBaseinfo selectByPrimaryKey(Map<String, Object> parms);

    int updateByPrimaryKeySelective(OrderBaseinfo record);

    int updateByPrimaryKey(OrderBaseinfo record);
}