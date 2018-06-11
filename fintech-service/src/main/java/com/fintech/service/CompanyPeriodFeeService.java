package com.fintech.service;

import java.util.List;
import java.util.Map;

import com.fintech.model.CompanyPeriodFee;

public interface CompanyPeriodFeeService {
	List<CompanyPeriodFee> selectByPrimaryKeyList(Map<String, Object>parms);

}
