package com.fintech.dao;

import java.util.List;
import java.util.Map;

import com.fintech.model.CompanyItem;

public interface CompanyItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CompanyItem record);

    int insertSelective(CompanyItem record);

    List<CompanyItem> selectByPrimaryKeyList(Map<String, Object>parms);

    int updateByPrimaryKeySelective(CompanyItem record);

    int updateByPrimaryKey(CompanyItem record);
}