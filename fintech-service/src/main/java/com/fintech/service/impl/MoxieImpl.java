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
import com.fintech.model.vo.moxie.BackMoxieBillVo;
import com.fintech.model.vo.moxie.BackMoxieFailVo;
import com.fintech.model.vo.moxie.BackMoxieReportVo;
import com.fintech.model.vo.moxie.BackMoxieTaskSubmitVo;
import com.fintech.model.vo.moxie.BackMoxieTaskVo;
import com.fintech.service.CreditVettingService;
import com.fintech.service.LogOrderService;
import com.fintech.service.MoxieService;
import com.fintech.service.RedisService;
import com.fintech.util.BeanUtils;
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

    /*
     * (非 Javadoc) <p>Title: insertSelective</p> <p>Description: </p>
     * 
     * @param record
     * 
     * @see com.fintech.service.MoxieService#insertSelective(com.fintech.model.LogMoxieinfo) 调用魔杖报告
     */
    @Override
    public void insertSelective(LogMoxieinfo record) throws Exception {
        System.out.println(appConfig.getMOXIE_TYPE_MXDATAEX().replace("{task_id}", record.getMoxieTaskId()).replace("{mobile}", record.getMoxieMobile())+"token "+appConfig.getMOXIE_TOKEN());
        StringBuffer result = new StringBuffer(MoxieUtil.doGetWithToken(appConfig.getMOXIE_TYPE_MXDATAEX().replace("{task_id}", record.getMoxieTaskId()).replace("{mobile}", record.getMoxieMobile()),appConfig.getMOXIE_TOKEN()));
        System.out.println(result);
    }

    /*
     * (非 Javadoc) <p>Title: backMoxieTaskSubmit</p> <p>Description: </p>
     * 
     * @param submitVo
     * 
     * @see com.fintech.service.MoxieService#backMoxieTaskSubmit(com.fintech.model.vo.moxie.BackMoxieTaskSubmitVo) 任务创建通知
     */
    @Override
    public void backMoxieTaskSubmit(BackMoxieTaskSubmitVo submitVo) throws Exception {
        LogMoxieinfo record = new LogMoxieinfo();
        record.setMoxieType(ConstantInterface.Enum.MoXieStatus.MOXIE_TYPE_MXREPORT.getKey());
        record.setMoxieIdcard(submitVo.getIdcard());
        record.setMoxieMobile(submitVo.getMobile());
        record.setMoxieName(submitVo.getName());
        record.setReportTime(DateUtils.stampToDateTime(submitVo.getTimestamp()));
        record.setMoxieTaskId(submitVo.getTask_id());
        record.setOrderId(submitVo.getUser_id());
        record.setMoxieStatus(ConstantInterface.Enum.ConstantNumber.ZERO.getKey());
        logMoxieinfoMapper.insertSelective(record);
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
        logger.info("EK 魔蝎日志 执行魔杖插入[taskId{}]操作时间[{}]", submitVo.getTask_id(),
                Thread.currentThread().getStackTrace()[1].getMethodName(), DateUtils.getDateTime());
        Map<String, Object> mzMap = new HashMap<>();
        mzMap.put("customer_id", appConfig.getMOZHANG_CUSTOMER_ID());
        mzMap.put("secret", appConfig.getMOZHANG_SECRET());
        try {
            LogMoxieinfo moxieinfo = logMoxieinfoMapper.selectByPrimaryKey(submitVo.getTask_id());
            if (moxieinfo != null) {
                String mzReslt = MoxieUtil.doPostWithApikey(
                        appConfig.getMOZHANG_TOKEN_URL().replace("{task_id}", submitVo.getTask_id()),
                        JSONObject.fromObject(mzMap), appConfig.getMOXIE_TOKEN());
                JSONObject mzJson = JSONObject.fromObject(mzReslt);
                String mzToken = mzJson.getString("token");
                String result = MoxieUtil.doGetWithToken(
                        appConfig.getMOZHANG_REPORT_URL().replace("{task_id}", submitVo.getTask_id()), mzToken);
                JSONObject jsonObject = JSONObject.fromObject(result);
                LogMozhanginfo logMozhanginfo = new LogMozhanginfo();
                logMozhanginfo.setMozhangIdcard(submitVo.getIdcard());
                logMozhanginfo.setMozhangName(submitVo.getName());
                logMozhanginfo.setMozhangMobile(submitVo.getMobile());
                logMozhanginfo.setMozhangTaskId(submitVo.getTask_id());
                logMozhanginfo.setMozhangContent(result);
                if (jsonObject.has("update_date")) {
                    logMozhanginfo.setReportTime(DateUtils.parse(jsonObject.getString("update_date")));
                }
                logMozhanginfo.setOrderId(submitVo.getUser_id());
                LogMozhanginfo mozhanginfo = logMozhanginfoMapper.selectByPrimaryKey(submitVo.getTask_id());
                if (mozhanginfo == null) {
                    logMozhanginfoMapper.insertSelective(logMozhanginfo);
                } else {
                    logMozhanginfo.setUpdateTime(new Date());
                    logMozhanginfoMapper.updateByPrimaryKeySelective(logMozhanginfo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * (非 Javadoc) <p>Title: backMoxieTask</p> <p>Description: </p>
     * 
     * @param submitVo
     * 
     * @see com.fintech.service.MoxieService#backMoxieTask(com.fintech.model.vo.moxie.BackMoxieTaskSubmitVo)
     *  授权登录完成，魔蝎调用客户在租户管理平台上配置的“任务授权登录结果通知URL”，
     *  异步通知客户授权结果，客户根据回调消息中“result”字段判断授权登录成功或失败，授权登录失败的情况，客户可对用户授权状态重置；
     */
    @Override
    public void backMoxieTask(BackMoxieTaskVo vo) {
        if(vo.isResult()) {
            LogMoxieinfo moxieinfo = logMoxieinfoMapper.selectByPrimaryKey(vo.getTask_id());
            if(moxieinfo!=null) {
                moxieinfo.setMoxieStatus(ConstantInterface.Enum.ConstantNumber.ONE.getKey());
                moxieinfo.setUpdateTime(new Date());
                logMoxieinfoMapper.updateByPrimaryKeySelective(moxieinfo);
            }
        }
    }

    /*
     * (非 Javadoc) <p>Title: backMoxieTaskTaskFail</p> <p>Description: </p>
     * 
     * @param submitVo
     * 
     * @see com.fintech.service.MoxieService#backMoxieTaskTaskFail(com.fintech.model.vo.moxie.BackMoxieTaskSubmitVo) 
     * 数据采集失败，魔蝎调用客户在租户管理平台上配置的“任务采集失败通知URL”，
     * 异步通知客户数据采集失败，客户可对用户授权状态重置；
     * 备注：数据采集失败的情况下，用户报告默认定义为失败，因此在配置报告通知的情况下，会发送用户报告通知，”result”结果为false。
     */
    @Override
    public void backMoxieFail(BackMoxieFailVo vo) throws Exception {
        if(vo.isResult()) {
            LogMoxieinfo logMoxieinfo = logMoxieinfoMapper.selectByPrimaryKey(vo.getTask_id());
            if(logMoxieinfo!=null) {
                logMoxieinfo.setMoxieStatus(ConstantInterface.Enum.ConstantNumber.THREE.getKey());
                logMoxieinfo.setUpdateTime(new Date());
                logMoxieinfoMapper.updateByPrimaryKeySelective(logMoxieinfo);
            }
        }
    }

    /*
     * (非 Javadoc) <p>Title: backMoxieBill</p> <p>Description: </p>
     * 
     * @param submitVo
     * 
     * @see com.fintech.service.MoxieService#backMoxieBill(com.fintech.model.vo.moxie.BackMoxieTaskSubmitVo) 
     * 数据采集完成，魔蝎调用客户在租户管理平台上配置的“账单通知URL”，异步通知客户数据采集成功，客户可调用“获取账号运营商数据”接口获取原始数据；
     */
    @Override
    public void backMoxieBill(BackMoxieBillVo vo) throws Exception {
            redisService.setVal(vo.getUser_id(),"000000", 60*60L);
            LogMoxieinfo moxieinfo = logMoxieinfoMapper.selectByPrimaryKey(vo.getTask_id());
            if(moxieinfo!=null) {
                moxieinfo.setMoxieStatus(ConstantInterface.Enum.ConstantNumber.FOUR.getKey());
                moxieinfo.setUpdateTime(new Date());
                logMoxieinfoMapper.updateByPrimaryKeySelective(moxieinfo);
            }
    }

    /*
     * (非 Javadoc) <p>Title: backMoxieReport</p> <p>Description: </p>
     * 
     * @param submitVo
     * 
     * @see com.fintech.service.MoxieService#backMoxieReport(com.fintech.model.vo.moxie.BackMoxieTaskSubmitVo) 报告处理完成，魔蝎调用客户在租户管理平台上配置的“用户报告通知URL”，异步通知客户报告处理结果，客户根据回调消息中“result”字段判断报告处理成功或失败，“result”=true报告处理成功，客户可调用“获取号码分析报告”获取用户报告，“result”=false报告处理失败，客户可对用户授权状态重置。
     */
    @Override
    public void backMoxieReport(BackMoxieReportVo vo) throws Exception {
        String url = appConfig.getMOXIE_TYPE_MXREPORT();
        redisService.setVal(vo.getUser_id(),"000000", 60*60L);
        try {
            if (vo.isResult()) {
                LogMoxieinfo moxieinfo = logMoxieinfoMapper.selectByPrimaryKey(vo.getTask_id());
                LogMoxieinfo logMoxieinfo=new LogMoxieinfo();
                BeanUtils.copyProperties(moxieinfo, logMoxieinfo);
                String result = MoxieUtil.doGetWithToken(url.replace("{task_id}", vo.getTask_id()).replace("{mobile}", vo.getMobile()),appConfig.getMOXIE_TOKEN());
                logMoxieinfo.setMoxieContent(result);
                logMoxieinfo.setReportTime(new Date());
                logMoxieinfo.setUpdateTime(new Date());
                logMoxieinfo.setMoxieStatus(ConstantInterface.Enum.ConstantNumber.FIVE.getKey());
                logMoxieinfo.setMoxieOnlineUrl(appConfig.getMOXIE_REPORT_DATA().replace("{message}", vo.getMessage()));
                logMoxieinfoMapper.updateByPrimaryKeySelective(logMoxieinfo);
                BackMoxieTaskSubmitVo submitVo=new BackMoxieTaskSubmitVo();
                submitVo.setMobile(moxieinfo.getMoxieMobile());
                submitVo.setIdcard(moxieinfo.getMoxieIdcard());
                submitVo.setTask_id(vo.getTask_id());
                submitVo.setUser_id(vo.getUser_id());
                insertSelectiveMoZhang(submitVo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
