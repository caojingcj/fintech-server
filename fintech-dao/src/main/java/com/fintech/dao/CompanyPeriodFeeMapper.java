package com.fintech.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fintech.model.CompanyPeriodFee;

public interface CompanyPeriodFeeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CompanyPeriodFee record);

    int insertSelective(CompanyPeriodFee record);

    List<CompanyPeriodFee> selectByPrimaryKeyList(Map<String, Object>parms);

    CompanyPeriodFee selectByCompanyIdAndPeriod(String companyId, Integer totalPeriod);

    int updateByPrimaryKeySelective(CompanyPeriodFee record);

    int updateByPrimaryKey(CompanyPeriodFee record);
    
    int count(@Param("companyId")String companyId);
}