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

import com.fintech.model.vo.CompanyChannelVo;
import com.fintech.service.CompanyChannelService;
import com.fintech.service.RedisService;
import com.fintech.util.CommonUtil;
import com.fintech.util.DateUtils;
import com.fintech.util.result.BaseResult;
import com.fintech.util.result.ResultUtils;

/**   
* @Title: CompanyAccountinfoController.java 
* @Package com.fintech.controller.company 
* @author qierkang xyqierkang@163.com   
* @date 2018年7月8日 下午8:57:00  
* @Description: TODO[ 商户咨询师控制器 ]
*/
@Controller
@RequestMapping(value = "manage/company/channel")
public class CompanyChannelController {
    public static Logger logger = LoggerFactory.getLogger(CompanyChannelController.class);

    Date nowDate = DateUtils.zeroDateSSS(new Date());
    @Autowired
    private CompanyChannelService companyChannelService;
    @Autowired
    private RedisService redisService;

    /** 
    * @Title: CompanyChannelController.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年7月8日 下午9:00:27  
    * @param @param vo
    * @param @return
    * @param @throws Exception    设定文件 
    * @Description: TODO[新增咨询师 ]
    * @throws 
    */
    @RequestMapping(value ="insertChannel",method = RequestMethod.POST)
    public @ResponseBody BaseResult insertChannel(@RequestBody CompanyChannelVo vo) throws Exception {
        try {
            redisService.tokenValidate(vo.getToken());
            logger.info("EK运营系统日志：方法名[{}]参数[{}]操作时间[{}]操作人[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),vo,DateUtils.getDateTime(),redisService.get(vo.getToken()));
            companyChannelService.insertSelective(vo);
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG);
        } catch (Exception e) {
            logger.error("ERROR EK运营系统日志：方法名[{}]报错[{}] 参数[{}] 报错时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),e.getMessage(),vo,DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
        }
    }
    
    /** 
    * @Title: CompanyChannelController.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年7月8日 下午9:00:15  
    * @param @param vo
    * @param @return
    * @param @throws Exception    设定文件 
    * @Description: TODO[ 更新咨询师 ]
    * @throws 
    */
    @RequestMapping(value ="updateChannel",method = RequestMethod.POST)
    public @ResponseBody BaseResult updateChannel(@RequestBody CompanyChannelVo vo) throws Exception {
        try {
            redisService.tokenValidate(vo.getToken());
            logger.info("EK运营系统日志：方法名[{}]参数[{}]操作时间[{}]操作人[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),vo,DateUtils.getDateTime(),redisService.get(vo.getToken()));
            companyChannelService.updateSelective(vo);
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG);
        } catch (Exception e) {
            logger.error("ERROR EK运营系统日志：方法名[{}]报错[{}] 参数[{}] 报错时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),e.getMessage(),vo,DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
        }
    }
    
    /** 
    * @Title: CompanyChannelController.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年7月8日 下午9:00:35  
    * @param @param id
    * @param @param token
    * @param @return
    * @param @throws Exception    设定文件 
    * @Description: TODO[ 删除咨询师 ]
    * @throws 
    */
    @RequestMapping(value ="deleteChannel",method = RequestMethod.GET)
    public @ResponseBody BaseResult deleteChannel(Integer id,String token) throws Exception {
        try {
            redisService.tokenValidate(token);
            logger.info("EK运营系统日志：方法名[{}]参数[{}]操作时间[{}]操作人[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),id,DateUtils.getDateTime(),redisService.get(token));
            companyChannelService.deleteSelective(id);
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG);
        } catch (Exception e) {
            logger.error("ERROR EK运营系统日志：方法名[{}]报错[{}] 参数[{}] 报错时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),e.getMessage(),id,DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
        }
    }
    
    @RequestMapping(value ="selectCompanyChannelList",method = RequestMethod.GET)
    public @ResponseBody BaseResult selectCompanyChannelList(CompanyChannelVo vo) throws Exception {
        try {
            redisService.tokenValidate(vo.getToken());
            logger.info("EK运营系统日志：方法名[{}]参数[{}]操作时间[{}]操作人[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),vo.getCompanyId(),DateUtils.getDateTime(),redisService.get(vo.getToken()));
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG,companyChannelService.selectByPrimaryKeyList(vo));
        } catch (Exception e) {
            logger.error("ERROR EK运营系统日志：方法名[{}]报错[{}] 参数[{}] 报错时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),e.getMessage(),vo.getCompanyId(),DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
        }
    }
   
}
