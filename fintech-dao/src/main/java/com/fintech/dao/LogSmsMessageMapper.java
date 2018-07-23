package com.fintech.dao;

import java.util.List;
import java.util.Map;

import com.fintech.model.LogSmsMessage;

public interface LogSmsMessageMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LogSmsMessage record);

    int insertSelective(LogSmsMessage record);

    LogSmsMessage selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LogSmsMessage record);

    int updateByPrimaryKey(LogSmsMessage record);
    
    List<LogSmsMessage> selectByPrimaryKeyList(Map<String, Object>parms);
}