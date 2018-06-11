package com.fintech.service;

import java.util.List;
import java.util.Map;

import com.fintech.model.CompanyItem;

public interface CompanyItemService {
	List<CompanyItem> selectByPrimaryKeyList(Map<String, Object> parms);
}
