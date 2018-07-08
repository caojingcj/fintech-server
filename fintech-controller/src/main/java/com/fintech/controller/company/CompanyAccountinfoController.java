package com.fintech.controller.company;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fintech.model.vo.CompanyAccountinfoVo;
import com.fintech.service.CompanyAccountinfoService;
import com.fintech.service.MasterBankService;
import com.fintech.service.RedisService;
import com.fintech.util.DateUtils;
import com.fintech.util.result.BaseResult;
import com.fintech.util.result.ResultUtils;

/**   
* @Title: CompanyAccountinfoController.java 
* @Package com.fintech.controller.company 
* @author qierkang xyqierkang@163.com   
* @date 2018年7月8日 下午8:57:00  
* @Description: TODO[ 商户清算账户控制器 ]
*/
@Controller
@RequestMapping(value = "manage/company/accountinfo")
public class CompanyAccountinfoController {
    public static Logger logger = LoggerFactory.getLogger(CompanyAccountinfoController.class);

    Date nowDate = DateUtils.zeroDateSSS(new Date());
    @Autowired
    private CompanyAccountinfoService companyAccountinfoService;
    @Autowired
    private MasterBankService masterBankService;
    @Autowired
    private RedisService redisService;

    /** 
    * @Title: CompanyAccountinfoController.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年7月8日 下午8:56:41  
    * @param @param vo
    * @param @return
    * @param @throws Exception    设定文件 
    * @Description: TODO[ 新增清算账户 ]
    * @throws 
    */
    @RequestMapping(value ="insertAccountInfo",method = RequestMethod.POST)
    public @ResponseBody BaseResult insertAccountInfo(@RequestBody CompanyAccountinfoVo vo) throws Exception {
        try {
            redisService.tokenValidate(vo.getToken());
            logger.info("EK运营系统日志：方法名[{}]参数[{}]操作时间[{}]操作人[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),vo,DateUtils.getDateTime(),redisService.get(vo.getToken()));
            companyAccountinfoService.insertSelective(vo);
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG);
        } catch (Exception e) {
            logger.error("ERROR EK运营系统日志：方法名[{}]报错[{}] 参数[{}] 报错时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),e.getMessage(),vo,DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
        }
    }
    
    /** 
    * @Title: CompanyAccountinfoController.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年7月8日 下午8:56:40  
    * @param @param vo
    * @param @return
    * @param @throws Exception    设定文件 
    * @Description: TODO[ 更新清算账户 ]
    * @throws 
    */
    @RequestMapping(value ="updateAccountInfo",method = RequestMethod.POST)
    public @ResponseBody BaseResult updateAccountInfo(@RequestBody CompanyAccountinfoVo vo) throws Exception {
        try {
            redisService.tokenValidate(vo.getToken());
            logger.info("EK运营系统日志：方法名[{}]参数[{}]操作时间[{}]操作人[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),vo,DateUtils.getDateTime(),redisService.get(vo.getToken()));
            companyAccountinfoService.updateSelective(vo);
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG);
        } catch (Exception e) {
            logger.error("ERROR EK运营系统日志：方法名[{}]报错[{}] 参数[{}] 报错时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),e.getMessage(),vo,DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
        }
    }
    
    /** 
    * @Title: CompanyAccountinfoController.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年7月8日 下午8:56:36  
    * @param @param id
    * @param @param token
    * @param @return
    * @param @throws Exception    设定文件 
    * @Description: TODO[ 删除清算账户 ]
    * @throws 
    */
    @RequestMapping(value ="deleteAccountInfo",method = RequestMethod.GET)
    public @ResponseBody BaseResult deleteAccountInfo(Integer id,String token) throws Exception {
        try {
            redisService.tokenValidate(token);
            logger.info("EK运营系统日志：方法名[{}]参数[{}]操作时间[{}]操作人[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),id,DateUtils.getDateTime(),redisService.get(token));
            companyAccountinfoService.deleteSelective(id);
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG);
        } catch (Exception e) {
            logger.error("ERROR EK运营系统日志：方法名[{}]报错[{}] 参数[{}] 报错时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),e.getMessage(),id,DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
        }
    }
    
    /** 
    * @Title: CompanyAccountinfoController.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年7月8日 下午10:08:27  
    * @param @param token
    * @param @return
    * @param @throws Exception    设定文件 
    * @Description: TODO[ 查詢銀行列表 ]
    * @throws 
    */
    @RequestMapping(value ="selectMasterBankList",method = RequestMethod.GET)
    public @ResponseBody BaseResult selectMasterBankList(String token) throws Exception {
    	try {
    		redisService.tokenValidate(token);
    		logger.info("EK运营系统日志：方法名[{}]操作时间[{}]操作人[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime(),redisService.get(token));
    		return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG,masterBankService.selectByPrimaryKeyList());
    	} catch (Exception e) {
    		logger.error("ERROR EK运营系统日志：方法名[{}]报错[{}]  报错时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),e.getMessage(),DateUtils.getDateTime());
    		return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
    	}
    }
    
   
}
