package com.fintech.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.dao.CompanyPeriodFeeMapper;
import com.fintech.model.CompanyPeriodFee;
import com.fintech.service.CompanyPeriodFeeService;
/**   
* @Title: CompanyPeriodFeeImpl.java 
* @Package com.fintech.service.impl 
* @author qierkang xyqierkang@163.com   
* @date 2018年6月12日 上午3:25:36  
* @Description: TODO[ 商户费率服务类 ]
*/
@Service
public class CompanyPeriodFeeImpl implements CompanyPeriodFeeService {
	@Autowired
	private CompanyPeriodFeeMapper companyPeriodFeeMapper;
	/* (非 Javadoc) 
	* <p>Title: selectByPrimaryKeyList</p> 
	* <p>Description: </p> 
	* @param parms
	* @return 根据商户编号查询商户费率
	* @see com.fintech.service.CompanyPeriodFeeService#selectByPrimaryKeyList(java.util.Map) 
	*/
	@Override
	public List<CompanyPeriodFee> selectByPrimaryKeyList(Map<String, Object> parms) {
		return companyPeriodFeeMapper.selectByPrimaryKeyList(parms);
	}

}
