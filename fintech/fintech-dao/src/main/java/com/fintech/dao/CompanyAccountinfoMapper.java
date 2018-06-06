package com.fintech.dao;

import com.fintech.model.CompanyAccountinfo;

public interface CompanyAccountinfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CompanyAccountinfo record);

    int insertSelective(CompanyAccountinfo record);

    CompanyAccountinfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CompanyAccountinfo record);

    int updateByPrimaryKey(CompanyAccountinfo record);
}