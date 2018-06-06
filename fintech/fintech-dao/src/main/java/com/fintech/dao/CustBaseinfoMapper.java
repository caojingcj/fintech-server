package com.fintech.dao;

import com.fintech.model.CustBaseinfo;

public interface CustBaseinfoMapper {
    int deleteByPrimaryKey(String custCellphone);

    int insert(CustBaseinfo record);

    int insertSelective(CustBaseinfo record);

    CustBaseinfo selectByPrimaryKey(String custCellphone);

    int updateByPrimaryKeySelective(CustBaseinfo record);

    int updateByPrimaryKey(CustBaseinfo record);
}