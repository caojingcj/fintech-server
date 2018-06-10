package com.fintech.service;

import com.fintech.model.CompanyBaseinfo;
import com.fintech.model.vo.CompanyBaseinfoVo;
import com.github.pagehelper.PageInfo;

public interface CompanyBaseinfoService {

    void insertCompanyBaseInfo(CompanyBaseinfo companyBaseinfo);

    PageInfo<CompanyBaseinfo> selectCompanyBaseInfos(CompanyBaseinfoVo companyBaseinfo)throws Exception;
}
