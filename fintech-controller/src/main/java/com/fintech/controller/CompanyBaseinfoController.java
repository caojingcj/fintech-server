package com.fintech.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fintech.service.CompanyBaseinfoService;
import com.fintech.util.DateUtils;
import com.fintech.util.result.BaseResult;
import com.fintech.util.result.ResultUtils;

@Controller
@RequestMapping(value = "/companyBaseinfo")
public class CompanyBaseinfoController {
    public static Logger logger = LoggerFactory.getLogger(CompanyBaseinfoController.class);

    Date nowDate = DateUtils.zeroDateSSS(new Date());
    @Autowired
    private CompanyBaseinfoService companyBaseinfoService;
    
    @RequestMapping(value ="selectCompanyBaseinfo")
    public @ResponseBody BaseResult selectCompanyBaseinfo() {
        // 获取用户信息
        logger.info("EK 方法名[{}]操作时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
        return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG,companyBaseinfoService.selectCompanyBaseInfo("000002"));
    }
}
