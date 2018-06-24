package com.fintech.service;

import com.fintech.model.vo.LogSmsMessageVo;

public interface LogSmsMessageService {
    void sendSmsMessage(LogSmsMessageVo record);
    
    String generateVerifyCode(String mobile, int length);
}
