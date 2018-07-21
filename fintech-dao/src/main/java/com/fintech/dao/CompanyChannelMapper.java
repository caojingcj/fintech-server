package com.fintech.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fintech.model.CompanyChannel;

public interface CompanyChannelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CompanyChannel record);

    int insertSelective(CompanyChannel record);

    List<CompanyChannel> selectByPrimaryKeyList(Map<String, Object>parms);

    int updateByPrimaryKeySelective(CompanyChannel record);

    int updateByPrimaryKey(CompanyChannel record);
    
    int count(@Param("companyId")String companyId);
}