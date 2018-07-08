package com.fintech.controller.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fintech.model.vo.LogSmsMessageVo;
import com.fintech.service.LogSmsMessageService;
import com.fintech.service.RedisService;
import com.fintech.util.DateUtils;
import com.fintech.util.result.ResultUtils;

/**   
* @Title: LogSmsMessageController.java 
* @Package com.fintech.controller.log 
* @author qierkang xyqierkang@163.com   
* @date 2018年7月8日 下午4:13:01  
* @Description: TODO[ 用一句话描述该文件做什么 ]
*/
@Controller
@RequestMapping(value = "/logsms")
public class LogSmsMessageController {
	@Autowired
	private LogSmsMessageService logSmsMessageService;
	@Autowired
	private RedisService redisService;
    private static final Logger logger = LoggerFactory.getLogger(LogSmsMessageController.class);

    /** 
    * @Title: LogSmsMessageController.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年7月8日 下午4:13:04  
    * @param @param vo
    * @param @return    设定文件 
    * @Description: TODO[ 这里用一句话描述这个方法的作用 ]
    * @throws 
    */
    @RequestMapping(value = "selectByPrimaryKeyList",method = RequestMethod.GET)
    public @ResponseBody Object selectByPrimaryKeyList(LogSmsMessageVo vo){
        logger.info("EK 参数[{}]方法名[{}]操作时间[{}]",vo,Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
    	try {
			redisService.tokenValidate(vo.getToken());
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG,logSmsMessageService.selectByPrimaryKeyList(vo));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ERROR EK 参数[{}] 报错[{}] 方法名[{}]报错时间[{}]",vo,e.getMessage(),Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
		}

    }
    
}
