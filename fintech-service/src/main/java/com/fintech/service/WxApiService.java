package com.fintech.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface WxApiService {
    Map<String, Object> wxOpenId(HttpServletRequest request, HttpServletResponse response) throws Exception;
    
    Map<String, Object> wxOrderList(HttpServletRequest request, HttpServletResponse response) throws Exception;

    Map<String, Object>wxJSSignature() throws Exception;
}
