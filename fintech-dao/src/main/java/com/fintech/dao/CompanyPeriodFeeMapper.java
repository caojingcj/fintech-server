package com.fintech.dao;

import com.fintech.model.CompanyPeriodFee;

public interface CompanyPeriodFeeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CompanyPeriodFee record);

    int insertSelective(CompanyPeriodFee record);

    CompanyPeriodFee selectByPrimaryKey(Integer id);

    CompanyPeriodFee selectByCompanyIdAndPeriod(String companyId, Integer totalPeriod);

    int updateByPrimaryKeySelective(CompanyPeriodFee record);

    int updateByPrimaryKey(CompanyPeriodFee record);
}