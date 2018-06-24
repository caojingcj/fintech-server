package com.fintech.service;

import com.fintech.model.LogSmsMessage;

public interface LogSmsMessageService {
    void insertSelective(LogSmsMessage record);
}
