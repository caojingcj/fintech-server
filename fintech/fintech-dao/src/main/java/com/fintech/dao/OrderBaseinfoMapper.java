package com.fintech.dao;

import com.fintech.model.OrderBaseinfo;

public interface OrderBaseinfoMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(OrderBaseinfo record);

    int insertSelective(OrderBaseinfo record);

    OrderBaseinfo selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(OrderBaseinfo record);

    int updateByPrimaryKey(OrderBaseinfo record);
}