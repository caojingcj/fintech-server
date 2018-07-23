package com.fintech.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.fintech.common.alipay.AlipaySmsDaYu;
import com.fintech.common.properties.AppConfig;
import com.fintech.dao.LogSmsMessageMapper;
import com.fintech.model.LogSmsMessage;
import com.fintech.model.vo.LogSmsMessageVo;
import com.fintech.service.LogSmsMessageService;
import com.fintech.service.RedisService;
import com.fintech.util.CommonUtil;
import com.fintech.util.DateUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;

@Service
public class LogSmsMessageImpl implements LogSmsMessageService {
    public static Logger logger = LoggerFactory.getLogger(LogSmsMessageImpl.class);

    @Autowired
    private AppConfig appConfig;
    @Autowired
    private RedisService redisService;
    @Autowired
    private LogSmsMessageMapper logSmsMessageMapper;

    @Override
    public void sendSmsMessage(LogSmsMessageVo record) throws Exception {
        try {
            SendSmsResponse response = AlipaySmsDaYu.sendSms(record.getSendMobile(), appConfig.getALI_SMS_TEMPCODE(),record.getSendCode(), 
                    appConfig.getALI_SMS_TEMPCONTENT().replace("smsCode", record.getSendCode()));
          //查明细
            logger.info("EK 发送验证码结果[{}] 方法名[{}]操作时间[{}]",response,Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
            Thread.sleep(3000L);
            if(StringUtils.isEmpty(response.getCode()) || !response.getCode().equals("OK")) {
               throw new Exception(response.getMessage());
            }
            Gson gson=new Gson();
            Map<String, Object>jsonMap=new HashMap<>();
            QuerySendDetailsResponse querySendDetailsResponse = AlipaySmsDaYu.querySendDetails(response.getBizId(),record.getSendMobile());
            LogSmsMessage logSmsMessage=new LogSmsMessage();
            jsonMap.put("Code", querySendDetailsResponse.getCode());
            jsonMap.put("RequestId", response.getRequestId());
            jsonMap.put("BizId", response.getBizId());
            for(QuerySendDetailsResponse.SmsSendDetailDTO smsSendDetailDTO : querySendDetailsResponse.getSmsSendDetailDTOs())
            {
                logSmsMessage.setSendContent(smsSendDetailDTO.getContent());
                logSmsMessage.setSendMobile(smsSendDetailDTO.getPhoneNum());
                logSmsMessage.setResultState(0);
                jsonMap.put("resultMes", smsSendDetailDTO);
            }
            logSmsMessage.setResultMsg(gson.toJson(jsonMap));
            logSmsMessage.setSendType(00);
            logSmsMessageMapper.insertSelective(logSmsMessage);
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public String generateVerifyCode(String mobile, int length) {
        assert length > 0 && length < 10 : "验证码的长度必须在0-10之间";
        String serverCode="";
        try {
            serverCode = redisService.get("code"+mobile);
            if (StringUtils.isBlank(serverCode)) {
                Random random = new Random();
                serverCode = String.valueOf(Math.abs(random.nextInt())).substring(0, length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serverCode;
    }

	/* (非 Javadoc) 
	* <p>Title: selectByPrimaryKeyList</p> 
	* <p>Description: </p> 
	* @param vo
	* @return
	* @throws Exception 
	* @see com.fintech.service.LogSmsMessageService#selectByPrimaryKeyList(com.fintech.model.vo.LogSmsMessageVo) 
	*查询短信列表
	*/
	@Override
	public PageInfo<LogSmsMessage> selectByPrimaryKeyList(LogSmsMessageVo vo) throws Exception {
		Map<String, Object> parms = CommonUtil.object2Map(vo);
		PageHelper.startPage(vo.getPageIndex(), vo.getPageSize());
		List<LogSmsMessage> logSmsMessages=logSmsMessageMapper.selectByPrimaryKeyList(parms);
		PageInfo<LogSmsMessage>pageLists=new PageInfo<>(logSmsMessages);
		return pageLists;
	}

}
