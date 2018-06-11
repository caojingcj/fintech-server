package com.fintech.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.dao.CompanyAccountinfoMapper;
import com.fintech.model.CompanyAccountinfo;
import com.fintech.service.CompanyAccountinfoService;
/**   
* @Title: CompanyAccountinfoImpl.java 
* @Package com.fintech.service.impl 
* @author qierkang xyqierkang@163.com   
* @date 2018年6月12日 上午2:24:06  
* @Description: TODO[ 商户清算账户服务类 ]
*/
@Service
public class CompanyAccountinfoImpl implements CompanyAccountinfoService {
	@Autowired
	private CompanyAccountinfoMapper companyAccountinfoMapper;

	@Override
	public List<CompanyAccountinfo> selectByPrimaryKeyList(Map<String, Object>parms) {
		return companyAccountinfoMapper.selectByPrimaryKeyList(parms);
	}
	
}
