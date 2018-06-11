package com.fintech.service;

import java.util.List;
import java.util.Map;

import com.fintech.model.CompanyAccountinfo;

public interface CompanyAccountinfoService {
List<CompanyAccountinfo> selectByPrimaryKeyList(Map<String, Object>parms);
}
