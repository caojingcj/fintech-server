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
import com.fintech.util.enumerator.ConstantInterface;
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
        logger.info("EK 运营系统日志：参数[帐号[{}]密码[{}]]方法名[{}]操作时间[{}]",name,password,Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
        try {
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG,userBaseinfoService.manageLogin(name, password));
        } catch (Exception e) {
            logger.error("ERROR EK 运营系统日志：参数[帐号[{}]密码[{}]]方法名[{}]操作时间[{}]]",name,password,Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
        }
    }
    
    @RequestMapping(value ="loginout",method = RequestMethod.GET)
    public @ResponseBody BaseResult appLoginout(String token) {
        try {
            logger.info("EK退出登录：操作人[{}]方法名[{}]操作时间[{}]",redisService.get(token),Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
            //根据key删除缓存 
            redisService.del(token);
            return ResultUtils.success("bye-bye");
        } catch (Exception e) {
            logger.error("ERROR EK参数[{}] 报错[{}] 方法名[{}]报错时间[{}]",token,e.getMessage(),
                    Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
        }
    }
}
