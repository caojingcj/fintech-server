package com.fintech.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface WxApiService {
    String wxOpenId(HttpServletRequest request, HttpServletResponse response)throws Exception;
}
