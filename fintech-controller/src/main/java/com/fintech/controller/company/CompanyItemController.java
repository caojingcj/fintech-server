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

import com.fintech.model.vo.CompanyItemVo;
import com.fintech.service.CompanyItemService;
import com.fintech.service.MasterCompanyItemService;
import com.fintech.service.RedisService;
import com.fintech.util.CommonUtil;
import com.fintech.util.DateUtils;
import com.fintech.util.result.BaseResult;
import com.fintech.util.result.ResultUtils;

/**   
* @Title: CompanyItemController.java 
* @Package com.fintech.controller.company 
* @author qierkang xyqierkang@163.com   
* @date 2018年7月8日 下午9:01:12  
* @Description: TODO[ 商户项目控制器 ]
*/
@Controller
@RequestMapping(value = "manage/company/item")
public class CompanyItemController {
    public static Logger logger = LoggerFactory.getLogger(CompanyItemController.class);

    Date nowDate = DateUtils.zeroDateSSS(new Date());
    @Autowired
    private CompanyItemService companyItemService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private MasterCompanyItemService masterCompanyItemService;

    /** 
    * @Title: CompanyItemController.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年7月8日 下午9:03:12  
    * @param @param vo
    * @param @return
    * @param @throws Exception    设定文件 
    * @Description: TODO[ 新增项目 ]
    * @throws 
    */
    @RequestMapping(value ="insertItem",method = RequestMethod.POST)
    public @ResponseBody BaseResult insertItem(@RequestBody CompanyItemVo vo) throws Exception {
        try {
            redisService.tokenValidate(vo.getToken());
            logger.info("EK运营系统日志：方法名[{}]参数[{}]操作时间[{}]操作人[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),vo,DateUtils.getDateTime(),redisService.get(vo.getToken()));
            companyItemService.insertSelective(vo);
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG);
        } catch (Exception e) {
            logger.error("ERROR EK运营系统日志：方法名[{}]报错[{}] 参数[{}] 报错时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),e.getMessage(),vo,DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
        }
    }
    
    /** 
    * @Title: CompanyItemController.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年7月8日 下午9:03:20  
    * @param @param vo
    * @param @return
    * @param @throws Exception    设定文件 
    * @Description: TODO[ 更新项目 ]
    * @throws 
    */
    @RequestMapping(value ="updateItem",method = RequestMethod.POST)
    public @ResponseBody BaseResult updateItem(@RequestBody CompanyItemVo vo) throws Exception {
        try {
            redisService.tokenValidate(vo.getToken());
            logger.info("EK运营系统日志：方法名[{}]参数[{}]操作时间[{}]操作人[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),vo,DateUtils.getDateTime(),redisService.get(vo.getToken()));
            companyItemService.updateSelective(vo);
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG);
        } catch (Exception e) {
            logger.error("ERROR EK运营系统日志：方法名[{}]报错[{}] 参数[{}] 报错时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),e.getMessage(),vo,DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
        }
    }
    
    /** 
    * @Title: CompanyItemController.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年7月8日 下午9:03:30  
    * @param @param id
    * @param @param token
    * @param @return
    * @param @throws Exception    设定文件 
    * @Description: TODO[ 删除项目 ]
    * @throws 
    */
    @RequestMapping(value ="deleteItem",method = RequestMethod.GET)
    public @ResponseBody BaseResult deleteItem(Integer id,String token) throws Exception {
        try {
            redisService.tokenValidate(token);
            logger.info("EK运营系统日志：方法名[{}]参数[{}]操作时间[{}]操作人[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),id,DateUtils.getDateTime(),redisService.get(token));
            companyItemService.deleteSelective(id);
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG);
        } catch (Exception e) {
            logger.error("ERROR EK运营系统日志：方法名[{}]报错[{}] 参数[{}] 报错时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),e.getMessage(),id,DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
        }
    }
    
    @RequestMapping(value ="selectMasterCompanyItemList",method = RequestMethod.GET)
    public @ResponseBody BaseResult selectMasterCompanyItemList(String token) throws Exception {
    	try {
    		redisService.tokenValidate(token);
    		logger.info("EK运营系统日志：方法名[{}]操作时间[{}]操作人[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime(),redisService.get(token));
    		return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG,masterCompanyItemService.selectByPrimaryKeyList());
    	} catch (Exception e) {
    		logger.error("ERROR EK运营系统日志：方法名[{}]报错[{}]报错时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),e.getMessage(),DateUtils.getDateTime());
    		return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
    	}
    }
    
    @RequestMapping(value ="selectCompanyItemList",method = RequestMethod.GET)
    public @ResponseBody BaseResult selectCompanyItemList(CompanyItemVo vo) throws Exception {
    	try {
    		redisService.tokenValidate(vo.getToken());
    		Map<String, Object>parms=CommonUtil.object2Map(vo);
    		logger.info("EK运营系统日志：方法名[{}]操作时间[{}]操作人[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime(),redisService.get(vo.getToken()));
    		return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG,companyItemService.selectByPrimaryKeyList(parms));
    	} catch (Exception e) {
    		logger.error("ERROR EK运营系统日志：方法名[{}]报错[{}]报错时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),e.getMessage(),DateUtils.getDateTime());
    		return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
    	}
    }
    
   
}
