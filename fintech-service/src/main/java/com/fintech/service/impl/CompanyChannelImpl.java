package com.fintech.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.dao.CompanyChannelMapper;
import com.fintech.model.CompanyChannel;
import com.fintech.service.CompanyChannelService;

/**   
* @Title: CompanyChannelImpl.java 
* @Package com.fintech.service.impl 
* @author qierkang xyqierkang@163.com   
* @date 2018年6月12日 上午3:25:14  
* @Description: TODO[ 商户渠道服务类 ]
*/
@Service
public class CompanyChannelImpl implements CompanyChannelService {

    @Autowired
    private CompanyChannelMapper companyChannelMapper;
	/* (非 Javadoc) 
	* <p>Title: selectByPrimaryKeyList</p> 
	* <p>Description: </p> 
	* @param parms
	* @return 根据商户编号查询商户渠道
	* @see com.fintech.service.CompanyChannelService#selectByPrimaryKeyList(java.util.Map) 
	*/
	@Override
	public List<CompanyChannel> selectByPrimaryKeyList(Map<String, Object> parms) {
		return companyChannelMapper.selectByPrimaryKeyList(parms);
	}

}
