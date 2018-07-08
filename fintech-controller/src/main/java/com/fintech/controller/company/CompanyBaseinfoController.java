package com.fintech.controller.company;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fintech.common.ObjectEmptyUtil;
import com.fintech.model.vo.CompanyBaseinfoVo;
import com.fintech.service.CompanyAccountinfoService;
import com.fintech.service.CompanyBaseinfoService;
import com.fintech.service.CompanyChannelService;
import com.fintech.service.CompanyItemService;
import com.fintech.service.CompanyPeriodFeeService;
import com.fintech.service.RedisService;
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
    @Autowired
    private RedisService redisService;
    /**
     * @throws Exception  
    * @Title: CompanyBaseinfoController.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年6月11日 上午12:16:09  
    * @param @param companyBaseinfo
    * @param @return    设定文件 
    * @Description: TODO[ 商户新增 ]
    * @throws 
    */
    @RequestMapping(value ="insertCompanyBaseInfo")
    public @ResponseBody BaseResult insertCompanyBaseInfo(@RequestBody CompanyBaseinfoVo vo) throws Exception {
        try {
            redisService.tokenValidate(vo.getToken());
            logger.info("EK运营系统日志：方法名[{}]参数[{}]操作时间[{}]操作人[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),vo,DateUtils.getDateTime(),redisService.get(vo.getToken()));
            companyBaseinfoService.insertCompanyBaseInfo(vo);
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG);
        } catch (Exception e) {
            logger.error("ERROR EK运营系统日志：方法名[{}]报错[{}] 参数[{}] 报错时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),e.getMessage(),vo,DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
        }
    }
    
    /** 
    * @Title: CompanyBaseinfoController.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年6月12日 上午1:52:45  
    * @param @param vo
    * @param @return    设定文件 
    * @Description: TODO[ 查询商户列表 ]
    * @throws 
    */
    @RequestMapping(value ="selectCompanyBaseInfos")
    public @ResponseBody BaseResult selectCompanyBaseInfos(CompanyBaseinfoVo vo) {
        logger.info("EK运营系统日志：参数[{}]方法名[{}]操作时间[{}]",vo,Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
        try {
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG,companyBaseinfoService.selectByPrimaryKeyList(vo));
        } catch (Exception e) {
            logger.error("ERROR EK运营系统日志：方法名[{}]报错[{}] 参数[{}] 报错时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),e.getMessage(),vo,DateUtils.getDateTime());
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
    public @ResponseBody BaseResult updateCompanyBaseInfoStatus(@RequestBody CompanyBaseinfoVo vo) {
    	try {
    	    redisService.tokenValidate(vo.getToken());
            logger.info("EK运营系统日志：方法名[{}]参数[商户编号{}][变更状态{}]操作时间[{}]操作人[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),vo.getCompanyId(),vo.getCompanyStatus(),DateUtils.getDateTime(),redisService.get(vo.getToken()));
    		companyBaseinfoService.updateCompanyBaseInfoStatus(vo);
    		return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG);
    	} catch (Exception e) {
            logger.error("ERROR EK运营系统日志：方法名[{}]报错[{}] 参数[{}] 报错时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),e.getMessage(),vo,DateUtils.getDateTime());
    		return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
    	}
    }
    /** 
    * @Title: CompanyBaseinfoController.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年6月12日 上午3:29:23  
    * @param @param vo
    * @param @return    设定文件 
    * @Description: TODO[ 查询商户详细信息 ]
    * @throws 
    */
    @RequestMapping(value ="selectCompanyBaseInfo")
    public @ResponseBody BaseResult selectCompanyBaseInfo(CompanyBaseinfoVo vo) {
        logger.info("EK运营系统日志：方法名[{}]参数[{}]操作时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),vo,DateUtils.getDateTime());
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
            logger.error("ERROR EK运营系统日志：方法名[{}]报错[{}] 参数[{}] 报错时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),e.getMessage(),vo,DateUtils.getDateTime());
    		return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
    	}
    }
}
