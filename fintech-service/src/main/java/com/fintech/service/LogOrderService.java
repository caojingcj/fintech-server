package com.fintech.service;

import org.springframework.transaction.annotation.Transactional;

import com.fintech.model.LogOrder;

@Transactional(rollbackFor = Exception.class)
public interface LogOrderService {

    void insertSelective(LogOrder record);
}
