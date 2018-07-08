package com.fintech.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.fintech.model.CompanyPeriodFee;
import com.fintech.model.vo.CompanyPeriodFeeVo;
@Transactional(rollbackFor = Exception.class)
public interface CompanyPeriodFeeService {
	List<CompanyPeriodFee> selectByPrimaryKeyList(Map<String, Object>parms);
	void insertSelective(CompanyPeriodFeeVo vo);
	
	void updateSelective(CompanyPeriodFeeVo vo);
	
	void deleteSelective(Integer id);
}
