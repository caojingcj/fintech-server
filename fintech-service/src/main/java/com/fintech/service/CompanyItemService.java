package com.fintech.service;

import java.util.List;
import java.util.Map;

import com.fintech.model.domain.CompanyItemDo;

public interface CompanyItemService {
	List<CompanyItemDo> selectByPrimaryKeyList(Map<String, Object> parms);
}
