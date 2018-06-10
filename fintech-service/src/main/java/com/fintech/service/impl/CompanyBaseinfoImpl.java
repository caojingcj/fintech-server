package com.fintech.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.common.CompanyEmptyUtil;
import com.fintech.dao.CompanyBaseinfoMapper;
import com.fintech.dao.procedure.CompanyProcedureMapper;
import com.fintech.model.CompanyBaseinfo;
import com.fintech.model.vo.CompanyBaseinfoVo;
import com.fintech.service.CompanyBaseinfoService;
import com.fintech.util.CommonUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class CompanyBaseinfoImpl implements CompanyBaseinfoService {

    @Autowired
    private CompanyBaseinfoMapper companyBaseinfoMapper;
    @Autowired
    private CompanyProcedureMapper companyProcedureMapper;

    @Override
    public void insertCompanyBaseInfo(CompanyBaseinfo companyBaseinfo) {
        CompanyEmptyUtil.CompanyEmpty(companyBaseinfo);
        companyBaseinfo.setCompanyId(companyProcedureMapper.generateCompanyId());
        companyBaseinfoMapper.insertSelective(companyBaseinfo);
    }

    @Override
    public PageInfo<CompanyBaseinfo> selectCompanyBaseInfos(CompanyBaseinfoVo companyBaseinfo) throws Exception {
        Map<String, Object> parms = CommonUtil.object2Map(companyBaseinfo);
        PageHelper.startPage(companyBaseinfo.getPageIndex(), companyBaseinfo.getPageSize());
        List<CompanyBaseinfo> companyBasseinfos=companyBaseinfoMapper.selectByPrimaryKeys(parms);
        PageInfo<CompanyBaseinfo> pageLists=new PageInfo<CompanyBaseinfo>(companyBasseinfos);
        return pageLists;
    }

}
