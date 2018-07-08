package com.fintech.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.fintech.model.CompanyChannel;
import com.fintech.model.vo.CompanyChannelVo;
@Transactional(rollbackFor = Exception.class)
public interface CompanyChannelService {
    List<CompanyChannel> selectByPrimaryKeyList(Map<String, Object> parms);
    
    void insertSelective(CompanyChannelVo vo);
    
    void updateSelective(CompanyChannelVo vo);
    
    void deleteSelective(Integer id);
}
