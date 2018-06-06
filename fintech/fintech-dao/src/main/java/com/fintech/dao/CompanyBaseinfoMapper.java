package com.fintech.dao;

import com.fintech.model.CompanyBaseinfo;

public interface CompanyBaseinfoMapper {
    int deleteByPrimaryKey(String companyId);

    int insert(CompanyBaseinfo record);

    int insertSelective(CompanyBaseinfo record);

    CompanyBaseinfo selectByPrimaryKey(String companyId);

    int updateByPrimaryKeySelective(CompanyBaseinfo record);

    int updateByPrimaryKey(CompanyBaseinfo record);
}