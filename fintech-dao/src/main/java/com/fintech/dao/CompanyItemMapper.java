package com.fintech.dao;

import com.fintech.model.CompanyItem;

public interface CompanyItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CompanyItem record);

    int insertSelective(CompanyItem record);

    CompanyItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CompanyItem record);

    int updateByPrimaryKey(CompanyItem record);
}