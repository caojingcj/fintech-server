package com.fintech.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.fintech.model.CompanyAccountinfo;
import com.fintech.model.vo.CompanyAccountinfoVo;
@Transactional(rollbackFor = Exception.class)
public interface CompanyAccountinfoService {
List<CompanyAccountinfo> selectByPrimaryKeyList(Map<String, Object>parms);

void insertSelective(CompanyAccountinfoVo vo);

void updateSelective(CompanyAccountinfoVo vo);

void deleteSelective(Integer id);

}
