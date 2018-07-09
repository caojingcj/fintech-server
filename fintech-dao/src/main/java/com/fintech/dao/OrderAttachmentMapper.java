package com.fintech.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fintech.model.OrderAttachment;

public interface OrderAttachmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderAttachment record);

    int insertSelective(OrderAttachment record);

    OrderAttachment selectByPrimaryKey(String orderId);
    
    List<OrderAttachment> selectByPrimaryKeyList(@Param("orderId") String orderId);

    int updateByPrimaryKeySelective(OrderAttachment record);

    int updateByPrimaryKey(OrderAttachment record);
    
    OrderAttachment selectByPrimaryKeySelective(Map<String, Object>parms);
}