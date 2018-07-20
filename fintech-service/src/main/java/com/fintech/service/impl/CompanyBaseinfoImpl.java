package com.fintech.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.common.ObjectEmptyUtil;
import com.fintech.dao.CompanyAccountinfoMapper;
import com.fintech.dao.CompanyBaseinfoMapper;
import com.fintech.dao.CompanyChannelMapper;
import com.fintech.dao.CompanyItemMapper;
import com.fintech.dao.CompanyPeriodFeeMapper;
import com.fintech.dao.procedure.CompanyProcedureMapper;
import com.fintech.model.CompanyAccountinfo;
import com.fintech.model.CompanyBaseinfo;
import com.fintech.model.vo.CompanyBaseinfoCombox;
import com.fintech.model.vo.CompanyBaseinfoVo;
import com.fintech.service.CompanyBaseinfoService;
import com.fintech.util.BeanUtils;
import com.fintech.util.CommonUtil;
import com.fintech.util.enumerator.ConstantInterface;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**   
* @Title: CompanyBaseinfoImpl.java 
* @Package com.fintech.service.impl 
* @author qierkang xyqierkang@163.com   
* @date 2018年6月12日 上午2:25:55  
* @Description: TODO[ 商户主体服务类 ]
*/
@Service
public class CompanyBaseinfoImpl implements CompanyBaseinfoService {

    @Autowired
    private CompanyBaseinfoMapper companyBaseinfoMapper;
    @Autowired
    private CompanyProcedureMapper companyProcedureMapper;
    @Autowired
    private CompanyAccountinfoMapper companyAccountinfoMapper;
    @Autowired
    private CompanyPeriodFeeMapper companyPeriodFeeMapper;
    @Autowired
    private CompanyItemMapper companyItemMapper;
    @Autowired
    private CompanyChannelMapper companyChannelMapper;

    /* (非 Javadoc) 
    * <p>Title: insertCompanyBaseInfo</p> 
    * <p>Description: </p> 
    * @param companyBaseinfo 
    * @see com.fintech.service.CompanyBaseinfoService#insertCompanyBaseInfo(com.fintech.model.CompanyBaseinfo)
    * 商户新增 
    */
    @Override
    public void insertCompanyBaseInfo(CompanyBaseinfoVo vo) {
        CompanyBaseinfo baseinfo=new CompanyBaseinfo();
        BeanUtils.copyProperties(vo, baseinfo);
        ObjectEmptyUtil.CompanyEmpty(baseinfo);
        baseinfo.setCompanyId(companyProcedureMapper.generateCompanyId());
        companyBaseinfoMapper.insertSelective(baseinfo);
    }

    /* (非 Javadoc) 
    * <p>Title: selectByPrimaryKeyList</p> 
    * <p>Description: </p> 
    * @param companyBaseinfo
    * @return查询商户列表
    * @throws Exception 
    * @see com.fintech.service.CompanyBaseinfoService#selectByPrimaryKeyList(com.fintech.model.vo.CompanyBaseinfoVo) 
    */
    @Override
    public PageInfo<CompanyBaseinfo> selectByPrimaryKeyList(CompanyBaseinfoVo companyBaseinfo) throws Exception {
        Map<String, Object> parms = CommonUtil.object2Map(companyBaseinfo);
        PageHelper.startPage(companyBaseinfo.getPageIndex(), companyBaseinfo.getPageSize());
        List<CompanyBaseinfo> companyBasseinfos=companyBaseinfoMapper.selectByPrimaryKeyList(parms);
        PageInfo<CompanyBaseinfo> pageLists=new PageInfo<CompanyBaseinfo>(companyBasseinfos);
        return pageLists;
    }

	/* (非 Javadoc) 
	* <p>Title: updateCompanyBaseInfoStatus</p> 
	* <p>Description: </p> 
	* @param companyBaseinfo 
	* @see com.fintech.service.CompanyBaseinfoService#updateCompanyBaseInfoStatus(com.fintech.model.CompanyBaseinfo)
	* 商户启用、禁用 
	*/
	@Override
	public void updateCompanyBaseInfoStatus(CompanyBaseinfoVo vo) {
		CompanyBaseinfo baseinfo=companyBaseinfoMapper.selectByPrimaryKeyInfo(vo.getCompanyId());
		baseinfo.setCompanyStatus(vo.getCompanyStatus());
		companyBaseinfoMapper.updateByPrimaryKeySelective(baseinfo);
	}

	/* (非 Javadoc) 
	* <p>Title: selectCompanyBaseInfo</p> 
	* <p>Description: </p> 
	* @param companyBaseinfo
	* @return
	* @throws Exception 
	* @see com.fintech.service.CompanyBaseinfoService#selectCompanyBaseInfo(com.fintech.model.vo.CompanyBaseinfoVo)
	* 根据商户编号查询商户基本信息
	*/
	@Override
	public List<CompanyBaseinfo> selectByPrimaryKey(Map<String, Object>parms) {
		return companyBaseinfoMapper.selectByPrimaryKey(parms);
	}

    @Override
    public CompanyBaseinfo selectByPrimaryKeyInfo(String companyId) {
        return companyBaseinfoMapper.selectByPrimaryKeyInfo(companyId);
    }

	@Override
	public Map<String, Object> selectCompanyBaseInfoDetails(String companyId) throws Exception {
		ObjectEmptyUtil.isEmptyByName(companyId);
		Map<String, Object>parms=new HashMap<>();
		parms.put("companyId", companyId);
		Map<String, Object>companyInfo=new HashMap<>();
		companyInfo.put("baseInfo", companyBaseinfoMapper.selectByPrimaryKeyInfo(companyId));
		return companyInfo;
	}

	/* (非 Javadoc) 
	* <p>Title: selectCompanyCombox</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.fintech.service.CompanyBaseinfoService#selectCompanyCombox() 
	*/
	@Override
	public List<CompanyBaseinfoCombox> selectCompanyCombox() {
		Map<String, Object>params=new HashMap<>();
		params.put("companyStatus", ConstantInterface.Enum.ConstantNumber.ONE.getKey());
		List<CompanyBaseinfoCombox> baseinfoComboxs=new ArrayList<>();
		for (CompanyBaseinfo companyBaseinfo : companyBaseinfoMapper.selectByPrimaryKeyList(params)) {
			CompanyBaseinfoCombox box=new CompanyBaseinfoCombox();
			box.setCompanyId(companyBaseinfo.getCompanyId());
			box.setCompanyName(companyBaseinfo.getCompanyId()+"-"+companyBaseinfo.getCompanyName());
			baseinfoComboxs.add(box);
		}
		return baseinfoComboxs;
	}

}
