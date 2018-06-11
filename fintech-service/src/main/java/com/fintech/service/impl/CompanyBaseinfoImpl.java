package com.fintech.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.common.ObjectEmptyUtil;
import com.fintech.dao.CompanyBaseinfoMapper;
import com.fintech.dao.procedure.CompanyProcedureMapper;
import com.fintech.model.CompanyBaseinfo;
import com.fintech.model.vo.CompanyBaseinfoVo;
import com.fintech.service.CompanyBaseinfoService;
import com.fintech.util.CommonUtil;
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

    @Override
    public void insertCompanyBaseInfo(CompanyBaseinfo companyBaseinfo) {
        ObjectEmptyUtil.CompanyEmpty(companyBaseinfo);
        companyBaseinfo.setCompanyId(companyProcedureMapper.generateCompanyId());
        companyBaseinfoMapper.insertSelective(companyBaseinfo);
    }

    @Override
    public PageInfo<CompanyBaseinfo> selectByPrimaryKeyList(CompanyBaseinfoVo companyBaseinfo) throws Exception {
        Map<String, Object> parms = CommonUtil.object2Map(companyBaseinfo);
        PageHelper.startPage(companyBaseinfo.getPageIndex(), companyBaseinfo.getPageSize());
        List<CompanyBaseinfo> companyBasseinfos=companyBaseinfoMapper.selectByPrimaryKeyList(parms);
        PageInfo<CompanyBaseinfo> pageLists=new PageInfo<CompanyBaseinfo>(companyBasseinfos);
        return pageLists;
    }

	@Override
	public void updateCompanyBaseInfoStatus(CompanyBaseinfo companyBaseinfo) {
		Object[] obj={"companyId","companyStatus"};//通用属性判断
		ObjectEmptyUtil.isObjectEmptyByName(obj,companyBaseinfo);
		companyBaseinfoMapper.updateByPrimaryKeySelective(companyBaseinfo);
	}

	/* (非 Javadoc) 
	* <p>Title: selectCompanyBaseInfo</p> 
	* <p>Description: </p> 
	* @param companyBaseinfo
	* @return
	* @throws Exception 
	* @see com.fintech.service.CompanyBaseinfoService#selectCompanyBaseInfo(com.fintech.model.vo.CompanyBaseinfoVo)
	* 根据商户编号查询 商户基本资料 
	*/
	@Override
	public List<CompanyBaseinfo> selectByPrimaryKey(Map<String, Object>parms) throws Exception {
		return companyBaseinfoMapper.selectByPrimaryKey(parms);
	}

}
