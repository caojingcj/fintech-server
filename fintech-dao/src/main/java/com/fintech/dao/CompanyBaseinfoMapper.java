package com.fintech.dao;

import java.util.List;
import java.util.Map;

import com.fintech.model.CompanyBaseinfo;

public interface CompanyBaseinfoMapper {
    
    CompanyBaseinfo selectByPrimaryKeyInfo(String companyId);
    
    int deleteByPrimaryKey(String companyId);

    int insert(CompanyBaseinfo record);

    int insertSelective(CompanyBaseinfo record);

    List<CompanyBaseinfo> selectByPrimaryKey(Map<String, Object>params);
    
    List<CompanyBaseinfo> selectByPrimaryKeyList(Map<String, Object>params);

    int updateByPrimaryKeySelective(CompanyBaseinfo record);

    int updateByPrimaryKey(CompanyBaseinfo record);
}