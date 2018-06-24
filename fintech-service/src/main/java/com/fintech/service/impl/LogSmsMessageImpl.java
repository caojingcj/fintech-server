package com.fintech.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.fintech.common.alipay.AlipaySmsDaYu;
import com.fintech.common.properties.AppConfig;
import com.fintech.model.LogSmsMessage;
import com.fintech.service.LogSmsMessageService;

@Service
public class LogSmsMessageImpl implements LogSmsMessageService {

    @Autowired
    private AppConfig config;

    @Override
    public void insertSelective(LogSmsMessage record) {
        try {
            SendSmsResponse response = AlipaySmsDaYu.sendSms("13813948485", "1234", config.getALI_SMS_TEMPCODE(),
                    config.getALI_SMS_TEMPCONTENT().replace("smsCode", "2343"));
            
            
            
        } catch (ClientException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
