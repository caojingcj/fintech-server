package com.fintech.dao;

import java.util.List;
import java.util.Map;

import com.fintech.model.CompanyBaseinfo;

public interface CompanyBaseinfoMapper {
    
    CompanyBaseinfo selectByPrimaryKeyInfo();
    
    int deleteByPrimaryKey(String companyId);

    int insert(CompanyBaseinfo record);

    int insertSelective(CompanyBaseinfo record);

    CompanyBaseinfo selectByPrimaryKey(String companyId);
    
    List<CompanyBaseinfo> selectByPrimaryKeys(Map<String, Object>params);

    int updateByPrimaryKeySelective(CompanyBaseinfo record);

    int updateByPrimaryKey(CompanyBaseinfo record);
}