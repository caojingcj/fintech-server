package com.fintech.dao;

import com.fintech.model.LogOcridcard;

public interface LogOcridcardMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LogOcridcard record);

    int insertSelective(LogOcridcard record);

    LogOcridcard selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LogOcridcard record);

    int updateByPrimaryKey(LogOcridcard record);
}