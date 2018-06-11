package com.fintech.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.dao.CompanyPeriodFeeMapper;
import com.fintech.model.CompanyPeriodFee;
import com.fintech.service.CompanyPeriodFeeService;
@Service
public class CompanyPeriodFeeImpl implements CompanyPeriodFeeService {
	@Autowired
	private CompanyPeriodFeeMapper companyPeriodFeeMapper;
	@Override
	public List<CompanyPeriodFee> selectByPrimaryKeyList(Map<String, Object> parms) {
		return companyPeriodFeeMapper.selectByPrimaryKeyList(parms);
	}

}
