package com.fintech.dao;

import com.fintech.model.CompanySeq;

public interface CompanySeqMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CompanySeq record);

    int insertSelective(CompanySeq record);

    CompanySeq selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CompanySeq record);

    int updateByPrimaryKey(CompanySeq record);
}