package com.fintech.dao;

import com.fintech.model.OrderAttachment;

public interface OrderAttachmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderAttachment record);

    int insertSelective(OrderAttachment record);

    OrderAttachment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderAttachment record);

    int updateByPrimaryKey(OrderAttachment record);
}