package com.fintech.controller.company;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fintech.common.ObjectEmptyUtil;
import com.fintech.model.CompanyBaseinfo;
import com.fintech.model.vo.CompanyBaseinfoVo;
import com.fintech.service.CompanyAccountinfoService;
import com.fintech.service.CompanyBaseinfoService;
import com.fintech.service.CompanyChannelService;
import com.fintech.service.CompanyItemService;
import com.fintech.service.CompanyPeriodFeeService;
import com.fintech.util.CommonUtil;
import com.fintech.util.DateUtils;
import com.fintech.util.result.BaseResult;
import com.fintech.util.result.ResultUtils;

/**   
* @Title: CompanyBaseinfoController.java 
* @Package com.fintech.controller 
* @author qierkang xyqierkang@163.com   
* @date 2018年6月11日 上午12:16:38  
* @Description: TODO[ 商户主体入口控制类 ]
*/
@Controller
@RequestMapping(value = "/companyBaseinfo")
public class CompanyBaseinfoController {
    public static Logger logger = LoggerFactory.getLogger(CompanyBaseinfoController.class);

    Date nowDate = DateUtils.zeroDateSSS(new Date());
    @Autowired
    private CompanyBaseinfoService companyBaseinfoService;
    @Autowired
    private CompanyAccountinfoService companyAccountinfoService;
    @Autowired
    private CompanyPeriodFeeService companyPeriodFeeService;
    @Autowired
    private CompanyItemService companyItemService;
    @Autowired
    private CompanyChannelService companyChannelService;
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
            logger.info("ERROR EK参数[{}] 报错[{}] 方法名[{}]报错时间[{}]",companyBaseinfo,e.getMessage(),Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
        }
    }
    
    /** 
    * @Title: CompanyBaseinfoController.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年6月12日 上午1:52:45  
    * @param @param vo
    * @param @return    设定文件 
    * @Description: TODO[ 商户主列表查询 ]
    * @throws 
    */
    @RequestMapping(value ="selectCompanyBaseInfos")
    public @ResponseBody BaseResult selectCompanyBaseInfos(CompanyBaseinfoVo vo) {
        logger.info("EK 参数[{}]方法名[{}]操作时间[{}]操作人[{}]",vo,Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
        try {
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG,companyBaseinfoService.selectByPrimaryKeyList(vo));
        } catch (Exception e) {
            logger.info("ERROR EK 参数[{}] 报错[{}] 方法名[{}]报错时间[{}]",vo,e.getMessage(),Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
        }
    }
    /** 
    * @Title: CompanyBaseinfoController.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年6月12日 上午1:53:00  
    * @param @param companyId
    * @param @param status
    * @param @return    设定文件 
    * @Description: TODO[ 商户1启用-1禁用 ]
    * @throws 
    */
    @RequestMapping(value ="updateCompanyBaseInfoStatus")
    public @ResponseBody BaseResult updateCompanyBaseInfoStatus(CompanyBaseinfo companyBaseinfo) {
    	logger.info("EK 参数[商户编号{}][变更状态{}]方法名[{}]操作时间[{}]操作人[{}]",companyBaseinfo.getCompanyId(),companyBaseinfo.getCompanyStatus(),Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
    	try {
    		companyBaseinfoService.updateCompanyBaseInfoStatus(companyBaseinfo);
    		return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG);
    	} catch (Exception e) {
    		logger.info("ERROR EK参数[商户编号{}][变更状态{}] 报错[{}] 方法名[{}]报错时间[{}]",companyBaseinfo.getCompanyId(),companyBaseinfo.getCompanyStatus(),e.getMessage(),Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
    		return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
    	}
    }
    @RequestMapping(value ="selectCompanyBaseInfo")
    public @ResponseBody BaseResult selectCompanyBaseInfo(CompanyBaseinfoVo vo) {
    	logger.info("EK 参数[商户编号{}]方法名[{}]操作时间[{}]操作人[{}]",vo.getCompanyId(),Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
    	try {
    		ObjectEmptyUtil.isEmptyByName(vo.getCompanyId());
    		Map<String, Object>parms=CommonUtil.object2Map(vo);
    		Map<String, Object>companyInfo=new HashMap<>();
    		companyInfo.put("baseInfo", companyBaseinfoService.selectByPrimaryKey(parms));
    		companyInfo.put("accountinfo", companyAccountinfoService.selectByPrimaryKeyList(parms));
    		companyInfo.put("periodFeeInfo", companyPeriodFeeService.selectByPrimaryKeyList(parms));
    		companyInfo.put("itemInfo", companyItemService.selectByPrimaryKeyList(parms));
    		companyInfo.put("channelInfo", companyChannelService.selectByPrimaryKeyList(parms));
    		return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG,companyInfo);
    	} catch (Exception e) {
    		logger.info("ERROR EK参数[商户编号{}] 报错[{}] 方法名[{}]报错时间[{}]",vo,e.getMessage(),Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
    		return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
    	}
    }
}
