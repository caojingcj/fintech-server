package com.fintech.dao;

import java.util.Map;

import com.fintech.model.LogMoxieinfo;

public interface LogMoxieinfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LogMoxieinfo record);

    int insertSelective(LogMoxieinfo record);

    LogMoxieinfo selectByPrimaryKey(String MoxieTaskId);
    
    LogMoxieinfo selectByPrimaryKeySelective(Map<String, Object>parms);

    int updateByPrimaryKeySelective(LogMoxieinfo record);

    int updateByPrimaryKey(LogMoxieinfo record);
}