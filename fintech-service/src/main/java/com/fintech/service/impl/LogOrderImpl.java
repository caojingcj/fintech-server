package com.fintech.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.dao.LogOrderMapper;
import com.fintech.model.LogOrder;
import com.fintech.service.LogOrderService;

@Service
public class LogOrderImpl implements LogOrderService{

    @Autowired
    private LogOrderMapper logOrderMapper;
    @Override
    public void insertSelective(LogOrder record) {
        logOrderMapper.insertSelective(record);
    }

}
