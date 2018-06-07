package com.fintech.dao;

import com.fintech.model.CompanyChannel;

public interface CompanyChannelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CompanyChannel record);

    int insertSelective(CompanyChannel record);

    CompanyChannel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CompanyChannel record);

    int updateByPrimaryKey(CompanyChannel record);
}