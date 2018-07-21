package com.fintech.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fintech.model.CompanyAccountinfo;

public interface CompanyAccountinfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CompanyAccountinfo record);

    int insertSelective(CompanyAccountinfo record);

    List<CompanyAccountinfo> selectByPrimaryKeyList(Map<String, Object>parms);
    
    CompanyAccountinfo selectByPrimaryKey(String companyId);

    int updateByPrimaryKeySelective(CompanyAccountinfo record);

    int updateByPrimaryKey(CompanyAccountinfo record);
    
    int count(@Param("companyId")String companyId);
}