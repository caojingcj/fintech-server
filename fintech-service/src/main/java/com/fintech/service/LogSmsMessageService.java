package com.fintech.service;

import com.fintech.model.vo.LogSmsMessageVo;

public interface LogSmsMessageService {
    void sendSmsMessage(LogSmsMessageVo record)throws Exception;
    
    String generateVerifyCode(String mobile, int length);
}
