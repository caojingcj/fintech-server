package com.fintech.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.common.moxie.MoxieUtil;
import com.fintech.common.properties.AppConfig;
import com.fintech.dao.LogMoxieinfoMapper;
import com.fintech.model.LogMoxieinfo;
import com.fintech.model.vo.moxie.BackMoxieTaskSubmitVo;
import com.fintech.service.MoxieService;

/**   
* @Title: LogMoxieinfoImpl.java 
* @Package com.fintech.service.impl 
* @author qierkang xyqierkang@163.com   
* @date 2018年6月24日 上午3:27:38  
* @Description: TODO[ 用一句话描述该文件做什么 ]
*/
@Service
public class MoxieImpl implements MoxieService {
    @Autowired
    private LogMoxieinfoMapper logMoxieinfoMapper;
    @Autowired
    private AppConfig config;

    @Override
    public void insertSelective(LogMoxieinfo record) {
        String url = "";
        switch (record.getMoxieType()) {
        case "mxdataex":
            url = config.getMOXIE_TYPE_MXDATAEX();
            break;
        case "basic":
            url = config.getMOXIE_TYPE_BASIC();
            break;
        case "bill":
            url = config.getMOXIE_TYPE_BILL();
            break;
        case "call":
            url = config.getMOXIE_TYPE_CALL();
            break;
        case "sms":
            url = config.getMOXIE_TYPE_SMS();
            break;
        }
        try {
            String result=MoxieUtil.doGetWithToken(url.replace("{task_id}", record.getMoxieTaskId()).replace("{mobile}", record.getMoxieMobile()));
            record.setMoxieContent(result);
            logMoxieinfoMapper.insertSelective(record);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void backMoxieTaskSubmit(BackMoxieTaskSubmitVo submitVo) {
        
    }

}
