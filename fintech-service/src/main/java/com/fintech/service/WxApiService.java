package com.fintech.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fintech.model.CompanyBaseinfo;
import com.fintech.model.domain.weixin.DataXmlPackageDo;

public interface WxApiService {
    Map<String, Object> wxOpenId(HttpServletRequest request, HttpServletResponse response) throws Exception;
    
    Map<String, Object> wxOrderList(HttpServletRequest request, HttpServletResponse response) throws Exception;

    Map<String, Object>wxJSSignature() throws Exception;
    
    String qrCodeCompany(String companyId);
    
    void wxQrCode(HttpServletRequest request, HttpServletResponse response)throws Exception;
    
    void pushMessageByUser(DataXmlPackageDo dataXmlPackageDO, HttpServletResponse response);
    
    String pushImgAndTextMessage(DataXmlPackageDo dataXmlPackageDO, CompanyBaseinfo baseinfo)throws Exception;

	void buildWxTableBar()throws Exception;
}
