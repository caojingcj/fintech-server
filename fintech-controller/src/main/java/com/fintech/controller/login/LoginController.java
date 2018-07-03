package com.fintech.controller.login;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fintech.util.result.ResultUtils;

@Controller
@RequestMapping(value = "/manage")
public class LoginController {
    
    @RequestMapping(value = "login",method = RequestMethod.GET)
    public @ResponseBody Object queryOrderByKeyPage(String name,String password){
//        logger.info("EK 参数[{}]方法名[{}]操作时间[{}]操作人[{}]",vo,Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
        try {
            Map<String, Object>map=new HashMap<>();
            if(name.equals("yiermei")&&password.equals("yrm123")) {
                map.put("status", "1");
                return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG,map);
            }
            if(name.equals("djfyy")&&password.equals("djfyy")) {
                map.put("status", "0");
                return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG,map);
            }
            return ResultUtils.error(ResultUtils.ERROR_CODE);
        } catch (Exception e) {
//            logger.error("ERROR EK 参数[{}] 报错[{}] 方法名[{}]报错时间[{}]",vo,e.getMessage(),Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
        }
    }

}
