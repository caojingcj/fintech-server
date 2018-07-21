package com.fintech.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.fintech.model.CompanyBaseinfo;
import com.fintech.model.vo.CompanyBaseinfoCombox;
import com.fintech.model.vo.CompanyBaseinfoVo;
import com.github.pagehelper.PageInfo;
@Transactional(rollbackFor = Exception.class)
public interface CompanyBaseinfoService {

    void insertCompanyBaseInfo(CompanyBaseinfoVo companyBaseinfo);

    void updateCompanyBaseInfoStatus(CompanyBaseinfoVo vo)throws Exception;
    
    PageInfo<CompanyBaseinfo> selectByPrimaryKeyList(CompanyBaseinfoVo companyBaseinfo)throws Exception;
    
    List<CompanyBaseinfo> selectByPrimaryKey(Map<String, Object>parms)throws Exception;
    
    List<CompanyBaseinfoCombox> selectCompanyCombox();
    
    CompanyBaseinfo selectByPrimaryKeyInfo(String companyId);
    
    Map<String, Object> selectCompanyBaseInfoDetails(String companyId)throws Exception;
    
    
}
