package com.fintech.service;

import com.fintech.model.OrderBaseinfo;
import com.fintech.xcpt.FintechException;

public interface OrderBaseinfoService {
    
    void insertSelective(OrderBaseinfo record) throws FintechException;

    void updateByPrimaryKeySelective(OrderBaseinfo record);
}
