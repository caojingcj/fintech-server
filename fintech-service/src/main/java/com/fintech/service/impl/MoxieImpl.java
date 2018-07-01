package com.fintech.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.common.moxie.MoxieUtil;
import com.fintech.common.properties.AppConfig;
import com.fintech.dao.LogMoxieinfoMapper;
import com.fintech.dao.LogMozhanginfoMapper;
import com.fintech.dao.OrderBaseinfoMapper;
import com.fintech.model.LogMoxieinfo;
import com.fintech.model.LogMozhanginfo;
import com.fintech.model.LogOrder;
import com.fintech.model.OrderBaseinfo;
import com.fintech.model.vo.moxie.BackMoxieTaskSubmitVo;
import com.fintech.service.CreditVettingService;
import com.fintech.service.LogOrderService;
import com.fintech.service.MoxieService;
import com.fintech.service.RedisService;
import com.fintech.util.DateUtils;
import com.fintech.util.enumerator.ConstantInterface;

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
    private static final Logger logger = LoggerFactory.getLogger(MoxieImpl.class);

    @Autowired
    private LogMoxieinfoMapper logMoxieinfoMapper;
    @Autowired
    private AppConfig appConfig;
    @Autowired
    private LogMozhanginfoMapper logMozhanginfoMapper;
    @Autowired
    private OrderBaseinfoMapper orderBaseinfoMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private LogOrderService logOrderService;
    @Autowired
    private CreditVettingService creditVettingService;
    /* (非 Javadoc) 
    * <p>Title: insertSelective</p> 
    * <p>Description: </p> 
    * @param record 
    * @see com.fintech.service.MoxieService#insertSelective(com.fintech.model.LogMoxieinfo) 
    * 调用魔杖报告
    */
    @Override
    public boolean insertSelective(LogMoxieinfo record) {
        String url = "";
        boolean flag=false;
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
            if(jsonObject.has("last_modify_time")) {
                LogMoxieinfo logMoxieinfo=logMoxieinfoMapper.selectByPrimaryKey(record.getMoxieTaskId());
                record.setMoxieContent(result);
                record.setReportTime(DateUtils.parse(jsonObject.get("last_modify_time").toString()));
                if(logMoxieinfo==null) {
                    logMoxieinfoMapper.insertSelective(record);
                    flag=true;
                }else {
                    record.setUpdateTime(new Date());
                    logMoxieinfoMapper.updateByPrimaryKeySelective(record);
                    flag=true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /* (非 Javadoc) 
    * <p>Title: backMoxieTaskSubmit</p> 
    * <p>Description: </p> 
    * @param submitVo 
    * @see com.fintech.service.MoxieService#backMoxieTaskSubmit(com.fintech.model.vo.moxie.BackMoxieTaskSubmitVo) 
    */
    @Override
    public void backMoxieTaskSubmit(BackMoxieTaskSubmitVo submitVo) {
        logger.info("魔蝎回调参数[{}]",submitVo.toString());
        LogMoxieinfo record=new LogMoxieinfo();
        record.setMoxieType("mxdataex");
        record.setMoxieIdcard(submitVo.getIdcard());
        record.setMoxieMobile(submitVo.getMobile());
        record.setMoxieName(submitVo.getName());
        record.setReportTime(DateUtils.stampToDateTime(submitVo.getTimestamp()));
        record.setMoxieTaskId(submitVo.getTask_id());
        record.setOrderId(submitVo.getUser_id());
        if(insertSelective(record)) {
//            insertSelectiveMoZhang(submitVo);
            Map<String, Object>map=new HashMap<>();
            map.put("orderId", record.getOrderId());
            LogMozhanginfo logMozhanginfo= logMozhanginfoMapper.selectByPrimaryKeySelective(map);
            if(logMozhanginfo!=null) {
                OrderBaseinfo baseinfo=orderBaseinfoMapper.selectByPrimaryKey(record.getOrderId());
                if(baseinfo.getOrderStatus().equals(String.valueOf(ConstantInterface.Enum.OrderStatus.ORDER_STATUS01.getKey()))) {
                    creditVettingService.creditVetting(record.getOrderId());
                }
            }
        }
    }
    
    /** 
    * @Title: MoxieImpl.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年6月30日 上午3:20:48  
    * @param @param submitVo    设定文件 
    * @Description: TODO[ 调用魔杖报告 ]
    * @throws 
    */
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
            if(jsonObject.has("update_date")) {
                logMozhanginfo.setReportTime(DateUtils.parse(jsonObject.getString("update_date")));
            }
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

    @Override
    public String resultMoxie(String orderId) throws Exception {
            redisService.tokenValidate(orderId);
            redisService.setVal(orderId,"000000", 60*60L);
            logOrderService.insertSelective(new LogOrder(orderId, ConstantInterface.Enum.OrderLogStatus.ORDER_STATUS03.getKey(), ConstantInterface.Enum.OrderStatus.ORDER_STATUS00.getKey(), null));
            return orderId;
    }

}
