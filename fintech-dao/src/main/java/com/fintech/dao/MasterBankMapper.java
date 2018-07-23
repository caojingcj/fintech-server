package com.fintech.dao;

import java.util.List;

import com.fintech.model.MasterBank;

public interface MasterBankMapper {
    int deleteByPrimaryKey(String bankCode);

    int insert(MasterBank record);

    int insertSelective(MasterBank record);

    MasterBank selectByPrimaryKey(String bankCode);
    
    List<MasterBank> selectByPrimaryKeyList();

    int updateByPrimaryKeySelective(MasterBank record);

    int updateByPrimaryKey(MasterBank record);
}