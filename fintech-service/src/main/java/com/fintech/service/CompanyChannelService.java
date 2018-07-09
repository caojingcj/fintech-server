package com.fintech.service;

import org.springframework.transaction.annotation.Transactional;

import com.fintech.model.CompanyChannel;
import com.fintech.model.vo.CompanyChannelVo;
import com.github.pagehelper.PageInfo;
@Transactional(rollbackFor = Exception.class)
public interface CompanyChannelService {
	
	PageInfo<CompanyChannel> selectByPrimaryKeyList(CompanyChannelVo vo)throws Exception;
    
    void insertSelective(CompanyChannelVo vo);
    
    void updateSelective(CompanyChannelVo vo);
    
    void deleteSelective(Integer id);
}
