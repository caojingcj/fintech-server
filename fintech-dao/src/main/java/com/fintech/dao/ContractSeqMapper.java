package com.fintech.dao;

import com.fintech.model.ContractSeq;

public interface ContractSeqMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ContractSeq record);

    int insertSelective(ContractSeq record);

    ContractSeq selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ContractSeq record);

    int updateByPrimaryKey(ContractSeq record);
}