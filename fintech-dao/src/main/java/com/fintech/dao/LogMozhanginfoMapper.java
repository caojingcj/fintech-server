package com.fintech.dao;

import java.util.Map;

import com.fintech.model.LogMozhanginfo;

public interface LogMozhanginfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LogMozhanginfo record);

    int insertSelective(LogMozhanginfo record);

    LogMozhanginfo selectByPrimaryKey(String mozhangTaskId);
    
    LogMozhanginfo selectByPrimaryKeySelective(Map<String, Object>parms);

    int updateByPrimaryKeySelective(LogMozhanginfo record);

    int updateByPrimaryKey(LogMozhanginfo record);
}