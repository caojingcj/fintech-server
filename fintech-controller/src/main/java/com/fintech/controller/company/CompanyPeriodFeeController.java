package com.fintech.controller.company;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fintech.model.vo.CompanyPeriodFeeVo;
import com.fintech.service.CompanyPeriodFeeService;
import com.fintech.service.RedisService;
import com.fintech.util.CommonUtil;
import com.fintech.util.DateUtils;
import com.fintech.util.result.BaseResult;
import com.fintech.util.result.ResultUtils;

/**   
* @Title: CompanyPeriodFeeController.java 
* @Package com.fintech.controller.company 
* @author qierkang xyqierkang@163.com   
* @date 2018年7月8日 下午9:06:09  
* @Description: TODO[ 商户期数控制器 ]
*/
@Controller
@RequestMapping(value = "manage/company/periodfee")
public class CompanyPeriodFeeController {
    public static Logger logger = LoggerFactory.getLogger(CompanyPeriodFeeController.class);

    Date nowDate = DateUtils.zeroDateSSS(new Date());
    @Autowired
    private CompanyPeriodFeeService companyPeriodFeeService;
    @Autowired
    private RedisService redisService;

    /** 
    * @Title: CompanyPeriodFeeController.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年7月8日 下午9:06:20  
    * @param @param vo
    * @param @return
    * @param @throws Exception    设定文件 
    * @Description: TODO[ 新增期数 ]
    * @throws 
    */
    @RequestMapping(value ="insertPeriodFee",method = RequestMethod.POST)
    public @ResponseBody BaseResult insertPeriodFee(@RequestBody CompanyPeriodFeeVo vo) throws Exception {
        try {
            redisService.tokenValidate(vo.getToken());
            logger.info("EK运营系统日志：方法名[{}]参数[{}]操作时间[{}]操作人[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),vo,DateUtils.getDateTime(),redisService.get(vo.getToken()));
            companyPeriodFeeService.insertSelective(vo);
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG);
        } catch (Exception e) {
            logger.error("ERROR EK运营系统日志：方法名[{}]报错[{}] 参数[{}] 报错时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),e.getMessage(),vo,DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
        }
    }
    
    /** 
    * @Title: CompanyPeriodFeeController.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年7月8日 下午9:06:27  
    * @param @param vo
    * @param @return
    * @param @throws Exception    设定文件 
    * @Description: TODO[ 更新期数 ]
    * @throws 
    */
    @RequestMapping(value ="updatePeriodFee",method = RequestMethod.POST)
    public @ResponseBody BaseResult updatePeriodFee(@RequestBody CompanyPeriodFeeVo vo) throws Exception {
        try {
            redisService.tokenValidate(vo.getToken());
            logger.info("EK运营系统日志：方法名[{}]参数[{}]操作时间[{}]操作人[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),vo,DateUtils.getDateTime(),redisService.get(vo.getToken()));
            companyPeriodFeeService.updateSelective(vo);
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG);
        } catch (Exception e) {
            logger.error("ERROR EK运营系统日志：方法名[{}]报错[{}] 参数[{}] 报错时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),e.getMessage(),vo,DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
        }
    }
    
    /** 
    * @Title: CompanyPeriodFeeController.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年7月8日 下午9:06:32  
    * @param @param id
    * @param @param token
    * @param @return
    * @param @throws Exception    设定文件 
    * @Description: TODO[ 删除期数 ]
    * @throws 
    */
    @RequestMapping(value ="deletePeriodFee",method = RequestMethod.GET)
    public @ResponseBody BaseResult deletePeriodFee(Integer id,String token) throws Exception {
        try {
            redisService.tokenValidate(token);
            logger.info("EK运营系统日志：方法名[{}]参数[{}]操作时间[{}]操作人[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),id,DateUtils.getDateTime(),redisService.get(token));
            companyPeriodFeeService.deleteSelective(id);
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG);
        } catch (Exception e) {
            logger.error("ERROR EK运营系统日志：方法名[{}]报错[{}] 参数[{}] 报错时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),e.getMessage(),id,DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
        }
    }
    
    @RequestMapping(value ="selectCompanyPeriodFeeList",method = RequestMethod.GET)
    public @ResponseBody BaseResult selectCompanyPeriodFeeList(CompanyPeriodFeeVo vo) throws Exception {
    	try {
    		redisService.tokenValidate(vo.getToken());
    		Map<String, Object>parms=CommonUtil.object2Map(vo);
    		logger.info("EK运营系统日志：方法名[{}]参数[{}]操作时间[{}]操作人[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),vo.getCompanyId(),DateUtils.getDateTime(),redisService.get(vo.getToken()));
    		return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG,companyPeriodFeeService.selectByPrimaryKeyList(parms));
    	} catch (Exception e) {
    		logger.error("ERROR EK运营系统日志：方法名[{}]报错[{}] 参数[{}] 报错时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),e.getMessage(),vo.getCompanyId(),DateUtils.getDateTime());
    		return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
    	}
    }
    
   
}
