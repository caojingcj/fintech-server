package com.fintech.dao;

import com.fintech.model.OrderDetailinfo;

public interface OrderDetailinfoMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(OrderDetailinfo record);

    int insertSelective(OrderDetailinfo record);

    OrderDetailinfo selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(OrderDetailinfo record);

    int updateByPrimaryKey(OrderDetailinfo record);
}