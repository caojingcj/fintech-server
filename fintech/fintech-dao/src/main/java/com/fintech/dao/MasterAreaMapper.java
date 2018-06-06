package com.fintech.dao;

import com.fintech.model.MasterArea;

public interface MasterAreaMapper {
    int deleteByPrimaryKey(Integer areaId);

    int insert(MasterArea record);

    int insertSelective(MasterArea record);

    MasterArea selectByPrimaryKey(Integer areaId);

    int updateByPrimaryKeySelective(MasterArea record);

    int updateByPrimaryKey(MasterArea record);
}