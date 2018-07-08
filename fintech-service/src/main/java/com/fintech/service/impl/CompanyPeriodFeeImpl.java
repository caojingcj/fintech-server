package com.fintech.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.dao.CompanyPeriodFeeMapper;
import com.fintech.model.CompanyPeriodFee;
import com.fintech.model.vo.CompanyPeriodFeeVo;
import com.fintech.service.CompanyPeriodFeeService;
import com.fintech.util.BeanUtils;
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
	/* (非 Javadoc) 
	* <p>Title: insertSelective</p> 
	* <p>Description: </p> 
	* @param vo 
	* @see com.fintech.service.CompanyPeriodFeeService#insertSelective(com.fintech.model.vo.CompanyPeriodFeeVo) 
	*增加期數
	*/
	@Override
	public void insertSelective(CompanyPeriodFeeVo vo) {
		CompanyPeriodFee periodFee=new CompanyPeriodFee();
		BeanUtils.copyProperties(vo, periodFee);
		companyPeriodFeeMapper.insertSelective(periodFee);
	}
	/* (非 Javadoc) 
	* <p>Title: updateSelective</p> 
	* <p>Description: </p> 
	* @param vo 
	* @see com.fintech.service.CompanyPeriodFeeService#updateSelective(com.fintech.model.vo.CompanyPeriodFeeVo) 
	*更新期數
	*/
	@Override
	public void updateSelective(CompanyPeriodFeeVo vo) {
		CompanyPeriodFee periodFee=new CompanyPeriodFee();
		BeanUtils.copyProperties(vo, periodFee);
		companyPeriodFeeMapper.updateByPrimaryKeySelective(periodFee);
	}
	/* (非 Javadoc) 
	* <p>Title: deleteSelective</p> 
	* <p>Description: </p> 
	* @param id 
	* @see com.fintech.service.CompanyPeriodFeeService#deleteSelective(java.lang.Integer) 
	*刪除期數
	*/
	@Override
	public void deleteSelective(Integer id) {
		companyPeriodFeeMapper.deleteByPrimaryKey(id);
	}

}
