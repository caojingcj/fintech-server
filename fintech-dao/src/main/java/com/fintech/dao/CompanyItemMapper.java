package com.fintech.dao;

import java.util.List;
import java.util.Map;

import com.fintech.model.CompanyItem;
import com.fintech.model.domain.CompanyItemDo;

public interface CompanyItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CompanyItem record);

    int insertSelective(CompanyItem record);

    List<CompanyItemDo> selectByPrimaryKeyList(Map<String, Object>parms);

    int updateByPrimaryKeySelective(CompanyItem record);

    int updateByPrimaryKey(CompanyItem record);
}