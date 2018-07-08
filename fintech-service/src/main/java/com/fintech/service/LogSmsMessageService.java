package com.fintech.service;

import org.springframework.transaction.annotation.Transactional;

import com.fintech.model.LogSmsMessage;
import com.fintech.model.vo.LogSmsMessageVo;
import com.github.pagehelper.PageInfo;
@Transactional(rollbackFor = Exception.class)
public interface LogSmsMessageService {
    void sendSmsMessage(LogSmsMessageVo record)throws Exception;
    
    String generateVerifyCode(String mobile, int length);
    
    PageInfo<LogSmsMessage> selectByPrimaryKeyList(LogSmsMessageVo vo)throws Exception;
}
