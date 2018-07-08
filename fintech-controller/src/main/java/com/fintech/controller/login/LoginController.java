package com.fintech.controller.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fintech.service.RedisService;
import com.fintech.service.UserBaseinfoService;
import com.fintech.util.DateUtils;
import com.fintech.util.result.BaseResult;
import com.fintech.util.result.ResultUtils;

@Controller
@RequestMapping(value = "/manage")
public class LoginController {
    public static Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private UserBaseinfoService userBaseinfoService;
	@Autowired
	private RedisService redisService;
	
    @RequestMapping(value = "login",method = RequestMethod.GET)
    public @ResponseBody Object queryOrderByKeyPage(String name,String password){
        logger.info("EK运营系统日志：方法名[{}]参数[帐号[{}]密码[{}]]操作时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),name,password,DateUtils.getDateTime());
        try {
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG,userBaseinfoService.manageLogin(name, password));
        } catch (Exception e) {
            logger.error("ERROR EK运营系统日志：方法名[{}]参数[帐号[{}]密码[{}]]操作时间[{}]]",Thread.currentThread().getStackTrace()[1].getMethodName(),name,password,DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
        }
    }
    
    @RequestMapping(value ="loginout",method = RequestMethod.GET)
    public @ResponseBody BaseResult appLoginout(String token) {
        try {
            logger.info("EK运营系统日志：退出登录操作人[{}]方法名[{}]操作时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),redisService.get(token),DateUtils.getDateTime());
            //根据key删除缓存 
            redisService.del(token);
            return ResultUtils.success("bye-bye");
        } catch (Exception e) {
            logger.error("ERROR EK运营系统日志：方法名[{}]参数[{}] 报错[{}] 报错时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),token,e.getMessage(),DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
        }
    }
}
