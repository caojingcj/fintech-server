package com.fintech.controller.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fintech.model.vo.UserReturnplanVo;
import com.fintech.service.RedisService;
import com.fintech.service.UserReturnPlanService;
import com.fintech.util.DateUtils;
import com.fintech.util.result.BaseResult;
import com.fintech.util.result.ResultUtils;

/**   
* @Title: UserReturnPlanController.java 
* @Package com.fintech.controller.user 
* @author qierkang xyqierkang@163.com   
* @date 2018年7月17日 下午10:51:52  
* @Description: TODO[ 还款计划控制类 ]
*/
@Controller
@RequestMapping(value = "/manage/user/returnplan")
public class UserReturnPlanController {
    public static Logger logger = LoggerFactory.getLogger(UserReturnPlanController.class);

    @Autowired
    private UserReturnPlanService userReturnPlanService;
    @Autowired
    private RedisService redisService;
    
    /** 
    * @Title: UserReturnPlanController.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年7月17日 下午10:53:48  
    * @param @param vo
    * @param @return    设定文件 
    * @Description: TODO[ 这里用一句话描述这个方法的作用 ]
    * @throws 
    */
    @RequestMapping(value ="selectUserReturnPlanList",method = RequestMethod.GET)
    public @ResponseBody BaseResult selectUserReturnPlanList(UserReturnplanVo vo) {
        logger.info("EK运营系统日志： 方法名[{}]参数[{}]操作时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),vo,DateUtils.getDateTime());
        try {
        	redisService.tokenValidate(vo.getToken());
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG,userReturnPlanService.selectByPrimaryKeyList(vo));
        } catch (Exception e) {
            logger.error("ERROR EK运营系统日志： 方法名[{}]报错[{}] 参数[{}] 报错时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),e.getMessage(),vo,DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
        }
    }
}
