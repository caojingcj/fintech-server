package com.fintech.controller.company;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fintech.common.CompanyEmptyUtil;
import com.fintech.model.CompanyBaseinfo;
import com.fintech.model.vo.CompanyBaseinfoVo;
import com.fintech.service.CompanyBaseinfoService;
import com.fintech.util.CommonUtil;
import com.fintech.util.DateUtils;
import com.fintech.util.result.BaseResult;
import com.fintech.util.result.ResultUtils;

/**   
* @Title: CompanyBaseinfoController.java 
* @Package com.fintech.controller 
* @author qierkang xyqierkang@163.com   
* @date 2018年6月11日 上午12:16:38  
* @Description: TODO[ 商户入口 ]
*/
@Controller
@RequestMapping(value = "/companyBaseinfo")
public class CompanyBaseinfoController {
    public static Logger logger = LoggerFactory.getLogger(CompanyBaseinfoController.class);

    Date nowDate = DateUtils.zeroDateSSS(new Date());
    @Autowired
    private CompanyBaseinfoService companyBaseinfoService;
    
    /** 
    * @Title: CompanyBaseinfoController.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年6月11日 上午12:16:09  
    * @param @param companyBaseinfo
    * @param @return    设定文件 
    * @Description: TODO[ 商户新增 ]
    * @throws 
    */
    @RequestMapping(value ="insertCompanyBaseInfo")
    public @ResponseBody BaseResult insertCompanyBaseInfo(CompanyBaseinfo companyBaseinfo) {
        logger.info("EK 方法名[{}]操作时间[{}]操作人[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
        try {
            companyBaseinfoService.insertCompanyBaseInfo(companyBaseinfo);
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG);
        } catch (Exception e) {
            logger.info("EK参数[{}] 报错[{}] 方法名[{}]报错时间[{}]",companyBaseinfo,e.getMessage(),Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
        }
    }
    
    @RequestMapping(value ="selectCompanyBaseInfos")
    public @ResponseBody BaseResult selectCompanyBaseInfos(CompanyBaseinfoVo vo) {
        logger.info("EK 方法名[{}]操作时间[{}]操作人[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
        try {
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG,companyBaseinfoService.selectCompanyBaseInfos(vo));
        } catch (Exception e) {
            logger.info("EK参数[{}] 报错[{}] 方法名[{}]报错时间[{}]",vo,e.getMessage(),Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
        }
    }
}
