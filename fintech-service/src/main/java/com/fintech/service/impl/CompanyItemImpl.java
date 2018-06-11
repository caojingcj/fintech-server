package com.fintech.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.dao.CompanyItemMapper;
import com.fintech.model.CompanyItem;
import com.fintech.service.CompanyItemService;

@Service
public class CompanyItemImpl implements CompanyItemService{
	@Autowired
	private CompanyItemMapper companyItemMapper;

	@Override
	public List<CompanyItem> selectByPrimaryKeyList(Map<String, Object> parms) {
		return companyItemMapper.selectByPrimaryKeyList(parms);
	}
}
