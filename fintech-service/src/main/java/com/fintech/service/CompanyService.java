package com.fintech.service;

import org.springframework.transaction.annotation.Transactional;

import com.fintech.xcpt.FintechException;

@Transactional(rollbackFor = { Exception.class,FintechException.class})
public interface CompanyService {

    /**
     * 设置商户上线
     * @param companyId 商户编号
     * @throws FintechException
     */
    public void updateCompanyOnline(String companyId) throws FintechException;

    /**
     * 设置商户下线
     * @param companyId 商户编号
     * @throws FintechException
     */
    public void updateCompanyOffline(String companyId) throws FintechException;

}
