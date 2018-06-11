package com.fintech.dao;

import java.util.List;
import java.util.Map;

import com.fintech.model.CompanyChannel;

public interface CompanyChannelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CompanyChannel record);

    int insertSelective(CompanyChannel record);

    List<CompanyChannel> selectByPrimaryKeyList(Map<String, Object>parms);

    int updateByPrimaryKeySelective(CompanyChannel record);

    int updateByPrimaryKey(CompanyChannel record);
}