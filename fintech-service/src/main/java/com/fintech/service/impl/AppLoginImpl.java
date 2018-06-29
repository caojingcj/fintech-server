package com.fintech.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.dao.CustBaseinfoMapper;
import com.fintech.model.CustBaseinfo;
import com.fintech.model.vo.LogSmsMessageVo;
import com.fintech.service.AppLoginService;
import com.fintech.service.LogSmsMessageService;
import com.fintech.service.RedisService;
import com.fintech.util.StringUtil;
import com.fintech.util.enumerator.ConstantInterface;
import com.fintech.util.sign.TokenUtil;

@Service
public class AppLoginImpl implements AppLoginService {

    @Autowired
    private CustBaseinfoMapper custBaseinfoMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private LogSmsMessageService logSmsMessageService;

    /* (非 Javadoc) 
    * <p>Title: appLoginVerification</p> 
    * <p>Description: </p> 
    * @param mobile
    * @param openId
    * @param code
    * @return
    * @throws Exception 
    * @see com.fintech.service.AppLoginService#appLoginVerification(java.lang.String, java.lang.String, java.lang.String) 
    */
    @Override
    public String appLoginVerification(String mobile,String openId, String code) throws Exception {
        String getCode=redisService.get(ConstantInterface.AppValidateConfig.ObjectRedisValidate.OBJECT_REDIS_CODE.getValue()+mobile); //放入redis 10分钟有效期
        if(StringUtil.isEmpty(getCode)||!getCode.equals(code)) {
            throw new Exception(ConstantInterface.AppValidateConfig.LoginValidate.LOGIN_200301.toString());
        }
        String token=TokenUtil.getToken();
        redisService.set(token,mobile);
        redisService.set(openId,token);
        if(custBaseinfoMapper.selectByPrimaryKey(mobile)==null) {
            CustBaseinfo custBaseinfo=new CustBaseinfo();
            custBaseinfo.setCustCellphone(mobile);
            custBaseinfoMapper.insertSelective(custBaseinfo);
        }
        return token;
    }

    @Override
    public void appLogin(String mobile) throws Exception {
        String sendCode=logSmsMessageService.generateVerifyCode(mobile, 4);
        redisService.setVal(ConstantInterface.AppValidateConfig.ObjectRedisValidate.OBJECT_REDIS_CODE.getValue()+mobile,sendCode, 60 * 10); //放入redis 10分钟有效期
        LogSmsMessageVo record=new LogSmsMessageVo();
        record.setSendMobile(mobile);
        record.setSendCode(sendCode);
        logSmsMessageService.sendSmsMessage(record);
    }

}
