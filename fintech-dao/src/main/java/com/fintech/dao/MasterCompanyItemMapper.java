package com.fintech.dao;

import com.fintech.model.MasterCompanyItem;

public interface MasterCompanyItemMapper {
    int deleteByPrimaryKey(String itemCode);

    int insert(MasterCompanyItem record);

    int insertSelective(MasterCompanyItem record);

    MasterCompanyItem selectByPrimaryKey(String itemCode);

    int updateByPrimaryKeySelective(MasterCompanyItem record);

    int updateByPrimaryKey(MasterCompanyItem record);
}