package com.fintech.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.dao.CompanyItemMapper;
import com.fintech.model.CompanyItem;
import com.fintech.service.CompanyItemService;

/**   
* @Title: CompanyItemImpl.java 
* @Package com.fintech.service.impl 
* @author qierkang xyqierkang@163.com   
* @date 2018年6月12日 上午3:25:25  
* @Description: TODO[ 商户项目服务类 ]
*/
@Service
public class CompanyItemImpl implements CompanyItemService{
	@Autowired
	private CompanyItemMapper companyItemMapper;

	/* (非 Javadoc) 
	* <p>Title: selectByPrimaryKeyList</p> 
	* <p>Description: </p> 
	* @param parms
	* @return 根据商户编号查询商户项目
	* @see com.fintech.service.CompanyItemService#selectByPrimaryKeyList(java.util.Map) 
	*/
	@Override
	public List<CompanyItem> selectByPrimaryKeyList(Map<String, Object> parms) {
		return companyItemMapper.selectByPrimaryKeyList(parms);
	}
}
