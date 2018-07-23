package com.fintech.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.dao.CompanyChannelMapper;
import com.fintech.model.CompanyBaseinfo;
import com.fintech.model.CompanyChannel;
import com.fintech.model.vo.CompanyChannelVo;
import com.fintech.service.CompanyChannelService;
import com.fintech.util.BeanUtils;
import com.fintech.util.CommonUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

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
	public PageInfo<CompanyChannel> selectByPrimaryKeyList(CompanyChannelVo vo) throws Exception {
		Map<String, Object> parms = CommonUtil.object2Map(vo);
        PageHelper.startPage(vo.getPageIndex(), vo.getPageSize());
        List<CompanyChannel> companyChannels=companyChannelMapper.selectByPrimaryKeyList(parms);
        PageInfo<CompanyChannel> pageLists=new PageInfo<CompanyChannel>(companyChannels);
        return pageLists;
	}
	/* (非 Javadoc) 
	* <p>Title: insertSelective</p> 
	* <p>Description: </p> 
	* @param vo 
	* @see com.fintech.service.CompanyChannelService#insertSelective(com.fintech.model.vo.CompanyChannelVo) 
	*新增咨询师
	*/
	@Override
	public void insertSelective(CompanyChannelVo vo) {
		CompanyChannel channel=new CompanyChannel();
		BeanUtils.copyProperties(vo, channel);
		companyChannelMapper.insertSelective(channel);
	}
	/* (非 Javadoc) 
	* <p>Title: updateSelective</p> 
	* <p>Description: </p> 
	* @param vo 
	* @see com.fintech.service.CompanyChannelService#updateSelective(com.fintech.model.vo.CompanyChannelVo) 
	*更新咨询师
	*/
	@Override
	public void updateSelective(CompanyChannelVo vo) {
		CompanyChannel channel=new CompanyChannel();
		BeanUtils.copyProperties(vo, channel);
		companyChannelMapper.updateByPrimaryKeySelective(channel);
	}
	/* (非 Javadoc) 
	* <p>Title: deleteSelective</p> 
	* <p>Description: </p> 
	* @param id 
	* @see com.fintech.service.CompanyChannelService#deleteSelective(java.lang.Integer) 
	*删除咨询师
	*/
	@Override
	public void deleteSelective(Integer id) {
		companyChannelMapper.deleteByPrimaryKey(id);
	}

}
