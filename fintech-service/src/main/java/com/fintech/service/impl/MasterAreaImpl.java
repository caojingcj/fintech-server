package com.fintech.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.dao.MasterAreaMapper;
import com.fintech.model.MasterArea;
import com.fintech.service.MasterAreaService;
import com.fintech.util.CommonUtil;
@Service
public class MasterAreaImpl implements MasterAreaService {
    @Autowired
    private MasterAreaMapper masterAreaMapper;
    @Override
    public List<MasterArea> queryMasterAreaByKey(Map<String, Object>params){
        return masterAreaMapper.selectByPrimaryKey(params);
    }
}
