package com.fintech.dao;

import com.fintech.model.MasterBank;

public interface MasterBankMapper {
    int deleteByPrimaryKey(String bankCode);

    int insert(MasterBank record);

    int insertSelective(MasterBank record);

    MasterBank selectByPrimaryKey(String bankCode);

    int updateByPrimaryKeySelective(MasterBank record);

    int updateByPrimaryKey(MasterBank record);
}