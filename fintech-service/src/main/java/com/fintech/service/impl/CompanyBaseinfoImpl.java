package com.fintech.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.dao.CompanyBaseinfoMapper;
import com.fintech.model.CompanyBaseinfo;
import com.fintech.service.CompanyBaseinfoService;

@Service
public class CompanyBaseinfoImpl implements CompanyBaseinfoService {

    @Autowired
    private CompanyBaseinfoMapper companyBaseinfoMapper;

    @Override
    public CompanyBaseinfo selectCompanyBaseInfo(String companyId) {
        CompanyBaseinfo ss=companyBaseinfoMapper.selectByPrimaryKeyInfo();
       return  companyBaseinfoMapper.selectByPrimaryKeyInfo();
    }

}
