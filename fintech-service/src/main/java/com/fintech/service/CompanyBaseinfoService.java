package com.fintech.service;

import java.util.List;
import java.util.Map;

import com.fintech.model.CompanyBaseinfo;
import com.fintech.model.vo.CompanyBaseinfoVo;
import com.github.pagehelper.PageInfo;

public interface CompanyBaseinfoService {

    void insertCompanyBaseInfo(CompanyBaseinfo companyBaseinfo);

    PageInfo<CompanyBaseinfo> selectByPrimaryKeyList(CompanyBaseinfoVo companyBaseinfo)throws Exception;
    
    List<CompanyBaseinfo> selectByPrimaryKey(Map<String, Object>parms)throws Exception;
    
    void updateCompanyBaseInfoStatus(CompanyBaseinfo companyBaseinfo);
    
    
}
