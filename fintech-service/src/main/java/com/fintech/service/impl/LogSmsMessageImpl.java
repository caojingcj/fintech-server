package com.fintech.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
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
import com.google.gson.Gson;

@Service
public class LogSmsMessageImpl implements LogSmsMessageService {

    @Autowired
    private AppConfig appConfig;
    @Autowired
    private RedisService redisService;
    @Autowired
    private LogSmsMessageMapper logSmsMessageMapper;

    @Override
    public void sendSmsMessage(LogSmsMessageVo record) {
        try {
            SendSmsResponse response = AlipaySmsDaYu.sendSms(record.getSendMobile(), record.getSendCode(), appConfig.getALI_SMS_TEMPCODE(),
                    appConfig.getALI_SMS_TEMPCONTENT().replace("smsCode", record.getSendCode()));
          //查明细
            if(response.getCode() != null && response.getCode().equals("OK")) {
                Map<String, Object>jsonMap=new HashMap<>();
                QuerySendDetailsResponse querySendDetailsResponse = AlipaySmsDaYu.querySendDetails(response.getBizId(),record.getSendMobile());
                LogSmsMessage logSmsMessage=new LogSmsMessage();
                jsonMap.put("Code", querySendDetailsResponse.getCode());
                jsonMap.put("RequestId", response.getRequestId());
                jsonMap.put("BizId", response.getBizId());
//                System.out.println("短信明细查询接口返回数据----------------");
//                System.out.println("Code=" + querySendDetailsResponse.getCode());
//                System.out.println("Message=" + querySendDetailsResponse.getMessage());
//                System.out.println("RequestId=" + response.getRequestId());
//                System.out.println("BizId=" + response.getBizId());
                int i = 0;
                Gson gson=new Gson();
                for(QuerySendDetailsResponse.SmsSendDetailDTO smsSendDetailDTO : querySendDetailsResponse.getSmsSendDetailDTOs())
                {
                    logSmsMessage.setSendContent(smsSendDetailDTO.getContent());
                    logSmsMessage.setSendMobile(smsSendDetailDTO.getPhoneNum());
                    logSmsMessage.setResultState(0);
                    jsonMap.put("resultMes", gson.toJson(smsSendDetailDTO));
//                    System.out.println("SmsSendDetailDTO["+i+"]:");
//                    System.out.println("Content=" + smsSendDetailDTO.getContent());
//                    System.out.println("ErrCode=" + smsSendDetailDTO.getErrCode());
//                    System.out.println("OutId=" + smsSendDetailDTO.getOutId());
//                    System.out.println("PhoneNum=" + smsSendDetailDTO.getPhoneNum());
//                    System.out.println("ReceiveDate=" + smsSendDetailDTO.getReceiveDate());
//                    System.out.println("SendDate=" + smsSendDetailDTO.getSendDate());
//                    System.out.println("SendStatus=" + smsSendDetailDTO.getSendStatus());
//                    System.out.println("Template=" + smsSendDetailDTO.getTemplateCode());
                }
//                System.out.println("TotalCount=" + querySendDetailsResponse.getTotalCount());
//                System.out.println("RequestId=" + querySendDetailsResponse.getRequestId());
                logSmsMessage.setResultMsg(gson.toJson(jsonMap));
                logSmsMessageMapper.insertSelective(logSmsMessage);
            }
        } catch (ClientException e) {
            // TODO Auto-generated catch block
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

}
