package com.fintech.dao;

import java.util.List;
import java.util.Map;

import com.fintech.model.MasterArea;

public interface MasterAreaMapper {
    List<MasterArea> selectByPrimaryKey(Map<String,Object> parms);
    
}