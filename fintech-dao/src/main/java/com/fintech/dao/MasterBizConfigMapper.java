package com.fintech.dao;

import com.fintech.model.MasterBizConfig;

public interface MasterBizConfigMapper {
    int deleteByPrimaryKey(String bizConfigCode);

    int insert(MasterBizConfig record);

    int insertSelective(MasterBizConfig record);

    MasterBizConfig selectByPrimaryKey(String bizConfigCode);

    int updateByPrimaryKeySelective(MasterBizConfig record);

    int updateByPrimaryKey(MasterBizConfig record);
}