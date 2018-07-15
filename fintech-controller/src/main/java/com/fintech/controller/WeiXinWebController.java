package com.fintech.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fintech.service.CompanyBaseinfoService;
import com.fintech.service.RedisService;
import com.fintech.service.WxApiService;
import com.fintech.util.DateUtils;
import com.fintech.util.result.ResultUtils;

@Controller
@RequestMapping(value = "manage/weixin")
public class WeiXinWebController {
    public static Logger logger = LoggerFactory.getLogger(WeiXinWebController.class);

    @Autowired
    private RedisService redisService;
    @Autowired
    private WxApiService wxApiService;
    
    @RequestMapping(value ="qrCodeCompany",method = RequestMethod.GET)
    public @ResponseBody Object qrCodeCompany(String token,String companyId) {
        try {
            redisService.tokenValidate(token);
            logger.info("EK运营系统日志： 方法名[{}]参数[{}]操作时间[{}]操作人[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),token,DateUtils.getDateTime(),redisService.get(token));
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG,wxApiService.qrCodeCompany(companyId));
        } catch (Exception e) {
            logger.error("ERROR EK运营系统日志： 方法名[{}]报错[{}] 参数[{}] 报错时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),e.getMessage(),token,DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
        }
    }
}
