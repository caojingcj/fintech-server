package com.fintech.service;

import org.springframework.transaction.annotation.Transactional;

import com.fintech.model.vo.LogSmsMessageVo;
@Transactional(rollbackFor = Exception.class)
public interface LogSmsMessageService {
    void sendSmsMessage(LogSmsMessageVo record)throws Exception;
    
    String generateVerifyCode(String mobile, int length);
}
