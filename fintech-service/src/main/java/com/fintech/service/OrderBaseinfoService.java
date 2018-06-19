package com.fintech.service;

import com.fintech.model.OrderBaseinfo;

public interface OrderBaseinfoService {
    
    void insertSelective(OrderBaseinfo record);

    void updateByPrimaryKeySelective(OrderBaseinfo record);
}
