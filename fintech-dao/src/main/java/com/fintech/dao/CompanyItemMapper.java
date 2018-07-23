package com.fintech.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fintech.model.CompanyItem;
import com.fintech.model.domain.CompanyItemDo;

public interface CompanyItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CompanyItem record);

    int insertSelective(CompanyItem record);

    List<CompanyItemDo> selectByPrimaryKeyList(Map<String, Object>parms);

    int updateByPrimaryKeySelective(CompanyItem record);

    int updateByPrimaryKey(CompanyItem record);
    
    int count(@Param("companyId")String companyId);
}