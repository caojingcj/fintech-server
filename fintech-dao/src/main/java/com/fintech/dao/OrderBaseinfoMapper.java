package com.fintech.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fintech.model.OrderBaseinfo;

public interface OrderBaseinfoMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(OrderBaseinfo record);

    int insertSelective(OrderBaseinfo record);
    
    List<OrderBaseinfo> selectByPrimaryKeyList(Map<String, Object>parms);
    
    OrderBaseinfo selectByPrimaryKey(@Param("orderId")String orderId);

    OrderBaseinfo selectByPrimaryKeySelective(Map<String, Object> parms);
    
    int updateByPrimaryKeySelective(OrderBaseinfo record);

    int updateByPrimaryKey(OrderBaseinfo record);
    
    Map<String, Object> selectByOrderAmountJudge(@Param("custCellphone")String custCellphone);
    
    List<OrderBaseinfo> selectQuarteCancelOrder();
    
}