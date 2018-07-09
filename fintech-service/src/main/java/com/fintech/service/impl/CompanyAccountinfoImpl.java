package com.fintech.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.dao.CompanyAccountinfoMapper;
import com.fintech.model.CompanyAccountinfo;
import com.fintech.model.vo.CompanyAccountinfoVo;
import com.fintech.service.CompanyAccountinfoService;
import com.fintech.util.BeanUtils;
import com.fintech.util.CommonUtil;
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

	/* (非 Javadoc) 
	* <p>Title: selectByPrimaryKeyList</p> 
	* <p>Description: </p> 
	* @param parms
	* @return 根据商户编号查询商户清算账户
	* @see com.fintech.service.CompanyAccountinfoService#selectByPrimaryKeyList(java.util.Map) 
	*/
	@Override
	public List<CompanyAccountinfo> selectByPrimaryKeyList(Map<String, Object> parms) {
		return companyAccountinfoMapper.selectByPrimaryKeyList(parms);
	}

	/* (非 Javadoc) 
	* <p>Title: insertSelective</p> 
	* <p>Description: </p> 
	* @param vo 
	* @see com.fintech.service.CompanyAccountinfoService#insertSelective(com.fintech.model.vo.CompanyAccountinfoVo) 
	*新增清算账户
	*/
	@Override
	public void insertSelective(CompanyAccountinfoVo vo) {
		CompanyAccountinfo accountinfo=new CompanyAccountinfo();
		BeanUtils.copyProperties(vo, accountinfo);
		companyAccountinfoMapper.insertSelective(accountinfo);
	}

	/* (非 Javadoc) 
	* <p>Title: updateSelective</p> 
	* <p>Description: </p> 
	* @param vo 
	* @see com.fintech.service.CompanyAccountinfoService#updateSelective(com.fintech.model.vo.CompanyAccountinfoVo) 
	*更出清算账户
	*/
	@Override
	public void updateSelective(CompanyAccountinfoVo vo) {
		CompanyAccountinfo accountinfo=new CompanyAccountinfo();
		BeanUtils.copyProperties(vo, accountinfo);
		companyAccountinfoMapper.updateByPrimaryKeySelective(accountinfo);
	}

	/* (非 Javadoc) 
	* <p>Title: deleteSelective</p> 
	* <p>Description: </p> 
	* @param id 
	* @see com.fintech.service.CompanyAccountinfoService#deleteSelective(java.lang.Integer) 
	*删除清算账户
	*/
	@Override
	public void deleteSelective(Integer id) {
		companyAccountinfoMapper.deleteByPrimaryKey(id);
	}
	
	
	
}
