package com.fintech.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.fintech.model.domain.CompanyItemDo;
import com.fintech.model.vo.CompanyItemVo;
@Transactional(rollbackFor = Exception.class)
public interface CompanyItemService {
	List<CompanyItemDo> selectByPrimaryKeyList(Map<String, Object> parms);
	
	void insertSelective(CompanyItemVo vo);
	
	void updateSelective(CompanyItemVo vo);
	
	void deleteSelective(Integer id);
}
