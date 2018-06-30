package com.fintech.service;

import com.fintech.xcpt.FintechException;

public interface QuarteFintechService {
    /** 
    * @Title: QuarteFintechService.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年6月30日 下午5:26:39  
    * @param     设定文件 
    * @Description: TODO[ 获取access_token 和 js ]
    * @throws 
    */
    void wxAuthentication() throws Exception;

    void cancelOrder() throws Exception;

    void quarteOverdueList();
    
    void quarteOverDueInfo()throws FintechException;
}
