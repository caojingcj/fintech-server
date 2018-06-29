package com.fintech.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.common.moxie.MoxieUtil;
import com.fintech.common.properties.AppConfig;
import com.fintech.dao.LogMoxieinfoMapper;
import com.fintech.dao.LogMozhanginfoMapper;
import com.fintech.model.LogMoxieinfo;
import com.fintech.model.LogMozhanginfo;
import com.fintech.model.vo.moxie.BackMoxieTaskSubmitVo;
import com.fintech.service.MoxieService;
import com.fintech.util.DateUtils;

import net.sf.json.JSONObject;

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
    private AppConfig appConfig;
    @Autowired
    private LogMozhanginfoMapper logMozhanginfoMapper;

    @Override
    public void insertSelective(LogMoxieinfo record) {
        String url = "";
        switch (record.getMoxieType()) {
        case "mxdataex":
            url = appConfig.getMOXIE_TYPE_MXDATAEX();
            break;
        case "basic":
            url = appConfig.getMOXIE_TYPE_BASIC();
            break;
        case "bill":
            url = appConfig.getMOXIE_TYPE_BILL();
            break;
        case "call":
            url = appConfig.getMOXIE_TYPE_CALL();
            break;
        case "sms":
            url = appConfig.getMOXIE_TYPE_SMS();
            break;
        }
        try {
            String result=MoxieUtil.doGetWithToken(url.replace("{task_id}", record.getMoxieTaskId()).replace("{mobile}", record.getMoxieMobile()),appConfig.getMOXIE_TOKEN());
            JSONObject jsonObject = JSONObject.fromObject(result);
            LogMoxieinfo logMoxieinfo=logMoxieinfoMapper.selectByPrimaryKey(record.getMoxieTaskId());
            record.setMoxieContent(result);
            record.setReportTime(DateUtils.parse(jsonObject.get("last_modify_time").toString()));
            if(logMoxieinfo==null) {
                logMoxieinfoMapper.insertSelective(record);
            }else {
                record.setUpdateTime(new Date());
                logMoxieinfoMapper.updateByPrimaryKeySelective(record);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void backMoxieTaskSubmit(BackMoxieTaskSubmitVo submitVo) {
        LogMoxieinfo record=new LogMoxieinfo();
        record.setMoxieType("mxdataex");
        record.setMoxieIdcard(submitVo.getIdcard());
        record.setMoxieMobile(submitVo.getMobile());
        record.setMoxieName(submitVo.getName());
        record.setReportTime(DateUtils.stampToDateTime(submitVo.getTimestamp()));
        record.setMoxieTaskId(submitVo.getTask_id());
        record.setOrderId(submitVo.getUser_id());
        insertSelective(record);
        insertSelectiveMoZhang(submitVo);
    }
    
    public void insertSelectiveMoZhang(BackMoxieTaskSubmitVo submitVo) {
        Map<String, Object>mzMap=new HashMap<>();
        mzMap.put("customer_id", appConfig.getMOZHANG_CUSTOMER_ID());
        mzMap.put("secret", appConfig.getMOZHANG_SECRET());
        try {
            String mzReslt=MoxieUtil.doPostWithApikey(appConfig.getMOZHANG_TOKEN_URL().replace("{task_id}", submitVo.getTask_id()), JSONObject.fromObject(mzMap),appConfig.getMOXIE_TOKEN());
            JSONObject mzJson = JSONObject.fromObject(mzReslt);
            String mzToken=mzJson.getString("token");
            String result=MoxieUtil.doGetWithToken(appConfig.getMOZHANG_REPORT_URL().replace("{task_id}", submitVo.getTask_id()),mzToken);
            JSONObject jsonObject = JSONObject.fromObject(result);
            LogMozhanginfo logMozhanginfo=new LogMozhanginfo();
            logMozhanginfo.setMozhangIdcard(submitVo.getIdcard());
            logMozhanginfo.setMozhangName(submitVo.getName());
            logMozhanginfo.setMozhangMobile(submitVo.getMobile());
            logMozhanginfo.setMozhangTaskId(submitVo.getTask_id());
            logMozhanginfo.setMozhangContent(result);
            logMozhanginfo.setReportTime(DateUtils.parse(jsonObject.getString("update_date")));
            logMozhanginfo.setOrderId(submitVo.getUser_id());
            LogMozhanginfo mozhanginfo= logMozhanginfoMapper.selectByPrimaryKey(submitVo.getTask_id());
            if(mozhanginfo==null) {
                logMozhanginfoMapper.insertSelective(logMozhanginfo);
            }else {
                logMozhanginfo.setUpdateTime(new Date());
                logMozhanginfoMapper.updateByPrimaryKeySelective(logMozhanginfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
