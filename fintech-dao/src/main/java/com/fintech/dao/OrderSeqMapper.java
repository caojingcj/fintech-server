package com.fintech.dao;

import com.fintech.model.OrderSeq;

public interface OrderSeqMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderSeq record);

    int insertSelective(OrderSeq record);

    OrderSeq selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderSeq record);

    int updateByPrimaryKey(OrderSeq record);
}