package com.fintech.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.dao.CompanyItemMapper;
import com.fintech.model.CompanyItem;
import com.fintech.model.domain.CompanyItemDo;
import com.fintech.model.vo.CompanyItemVo;
import com.fintech.service.CompanyItemService;
import com.fintech.util.BeanUtils;

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
	public List<CompanyItemDo> selectByPrimaryKeyList(Map<String, Object> parms) {
		return companyItemMapper.selectByPrimaryKeyList(parms);
	}

	/* (非 Javadoc) 
	* <p>Title: insertSelective</p> 
	* <p>Description: </p> 
	* @param vo 
	* @see com.fintech.service.CompanyItemService#insertSelective(com.fintech.model.vo.CompanyItemVo) 
	*新增项目
	*/
	@Override
	public void insertSelective(CompanyItemVo vo) {
		CompanyItem item=new CompanyItem();
		BeanUtils.copyProperties(vo, item);
		companyItemMapper.insertSelective(item);
	}

	/* (非 Javadoc) 
	* <p>Title: updateSelective</p> 
	* <p>Description: </p> 
	* @param vo 
	* @see com.fintech.service.CompanyItemService#updateSelective(com.fintech.model.vo.CompanyItemVo) 
	*修改项目
	*/
	@Override
	public void updateSelective(CompanyItemVo vo) {
		CompanyItem item=new CompanyItem();
		BeanUtils.copyProperties(vo, item);
		companyItemMapper.updateByPrimaryKeySelective(item);
	}

	/* (非 Javadoc) 
	* <p>Title: deleteSelective</p> 
	* <p>Description: </p> 
	* @param id 
	* @see com.fintech.service.CompanyItemService#deleteSelective(java.lang.Integer) 
	*删除项目
	*/
	@Override
	public void deleteSelective(Integer id) {
		companyItemMapper.deleteByPrimaryKey(id);
	}
}
