package com.fintech.controller.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fintech.service.AppLoginService;
import com.fintech.service.OrderBaseinfoService;
import com.fintech.service.RedisService;
import com.fintech.util.DateUtils;
import com.fintech.util.enumerator.ConstantInterface;
import com.fintech.util.result.BaseResult;
import com.fintech.util.result.ResultUtils;

/**   
* @Title: AppLoginController.java 
* @Package com.fintech.controller.login 
* @author qierkang xyqierkang@163.com   
* @date 2018年6月26日 上午4:19:09  
* @Description: TODO[ 登录控制器 ]
*/
@Controller
@RequestMapping(value = "app/appLogin")
public class AppLoginController {
    @Autowired
    private RedisService redisService;
    @Autowired
    private AppLoginService appLoginService;
    public static Logger logger = LoggerFactory.getLogger(AppLoginController.class);

    
    /** 
    * @Title: AppLoginController.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年6月25日 上午2:21:26  
    * @param @param mobile
    * @param @return    设定文件 
    * @Description: TODO[ 登录注册 ]
    * @throws 
    */
//    @RequestMapping(value ="appRegister")
//    public @ResponseBody BaseResult appRegister(String mobile) {
//        logger.info("EK 方法名[{}]操作时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
//        
//    }
    
    /** 
    * @Title: AppLoginController.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年6月25日 下午6:23:29  
    * @param @param mobile
    * @param @return    设定文件 
    * @Description: TODO[ 登录发送验证码 ]
    * @throws 
    */
    @RequestMapping(value ="appLogin",method = RequestMethod.GET)
    public @ResponseBody BaseResult appLogin(String mobile) {
        try {
            logger.info("EK 发送登录验证码[{}] 方法名[{}]操作时间[{}]",mobile,Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
            appLoginService.appLogin(mobile);
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG);
        } catch (Exception e) {
            logger.error("ERROR EK参数[{}] 报错[{}] 方法名[{}]报错时间[{}]",mobile,e.getMessage(),
                    Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
        }
    }
    
    /** 
    * @Title: AppLoginController.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年6月25日 下午6:23:37  
    * @param @param mobile
    * @param @return    设定文件 
    * @Description: TODO[ 登出 ]
    * @throws 
    */
    @RequestMapping(value ="appLoginout",method = RequestMethod.GET)
    public @ResponseBody BaseResult appLoginout(String token) {
        try {
            logger.info("EK 操作人[{}]方法名[{}]操作时间[{}]",redisService.get(token),Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
            System.out.println("清除前："+redisService.get(ConstantInterface.AppValidateConfig.ObjectRedisValidate.OBJECT_REDIS_TOKEN.getValue()+token));
            //根据key删除缓存 
            redisService.del(token);
            System.out.println("清除后："+redisService.get(ConstantInterface.AppValidateConfig.ObjectRedisValidate.OBJECT_REDIS_TOKEN.getValue()+token));
            return ResultUtils.success("bye-bye");
        } catch (Exception e) {
            logger.error("ERROR EK参数[{}] 报错[{}] 方法名[{}]报错时间[{}]",token,e.getMessage(),
                    Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
        }
    }
    
    /** 
    * @Title: AppLoginController.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年6月25日 下午6:23:43  
    * @param @param mobile
    * @param @param code
    * @param @return    设定文件 
    * @Description: TODO[ 校验验证码 ]
    * @throws 
    */
    @RequestMapping(value ="appLoginVerification",method = RequestMethod.GET)
    public @ResponseBody BaseResult appLoginVerification(String mobile,String openId,String code) {
        logger.info("EK 方法名[{}]操作时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
        try {
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG,appLoginService.appLoginVerification(mobile, openId,code));
        } catch (Exception e) {
            logger.error("ERROR EK参数[{}] 报错[{}] 方法名[{}]报错时间[{}]",mobile,e.getMessage(),Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
        }
    }
    
}
