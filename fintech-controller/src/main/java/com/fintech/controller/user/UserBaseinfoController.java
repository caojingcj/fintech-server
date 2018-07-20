package com.fintech.controller.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fintech.model.vo.UserBaseinfoVo;
import com.fintech.service.RedisService;
import com.fintech.service.UserBaseinfoService;
import com.fintech.util.DateUtils;
import com.fintech.util.result.BaseResult;
import com.fintech.util.result.ResultUtils;

/**   
* @Title: UserReturnPlanController.java 
* @Package com.fintech.controller.user 
* @author qierkang xyqierkang@163.com   
* @date 2018年7月17日 下午10:51:52  
* @Description: TODO[ 用户列表控制类 ]
*/
@Controller
@RequestMapping(value = "/manage/user/baseinfo")
public class UserBaseinfoController {
    public static Logger logger = LoggerFactory.getLogger(UserBaseinfoController.class);

    @Autowired
    private UserBaseinfoService userBaseinfoService;
    @Autowired
    private RedisService redisService;
    
    /** 
    * @Title: UserBaseinfoController.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年7月17日 下午11:02:17  
    * @param @param vo
    * @param @return    设定文件 
    * @Description: TODO[ 查询用户列表 ]
    * @throws 
    */
    @RequestMapping(value ="selectUserBaseInfoList",method = RequestMethod.GET)
    public @ResponseBody BaseResult selectUserReturnPlanList(UserBaseinfoVo vo) {
        logger.info("EK运营系统日志： 方法名[{}]参数[{}]操作时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),vo,DateUtils.getDateTime());
        try {
        	redisService.tokenValidate(vo.getToken());
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG,userBaseinfoService.selectByPrimaryKeyList(vo));
        } catch (Exception e) {
            logger.error("ERROR EK运营系统日志： 方法名[{}]报错[{}] 参数[{}] 报错时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),e.getMessage(),vo,DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
        }
    }
    
    /** 
    * @Title: UserBaseinfoController.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年7月19日 下午8:19:07  
    * @param @param vo
    * @param @return
    * @param @throws Exception    设定文件 
    * @Description: TODO[ 用户新增 ]
    * @throws 
    */
    @RequestMapping(value ="insertUserBaseInfo",method = RequestMethod.POST)
    public @ResponseBody BaseResult insertUserBaseInfo(@RequestBody UserBaseinfoVo vo) throws Exception {
        try {
            redisService.tokenValidate(vo.getToken());
            logger.info("EK运营系统日志：方法名[{}]参数[{}]操作时间[{}]操作人[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),vo,DateUtils.getDateTime(),redisService.get(vo.getToken()));
            userBaseinfoService.insertUserBaseInfo(vo);
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG);
        } catch (Exception e) {
            logger.error("ERROR EK运营系统日志：方法名[{}]报错[{}] 参数[{}] 报错时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),e.getMessage(),vo,DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
        }
    }
    
    /** 
    * @Title: UserBaseinfoController.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年7月19日 下午8:19:08  
    * @param @param vo
    * @param @return
    * @param @throws Exception    设定文件 
    * @Description: TODO[ 用户更新 ]
    * @throws 
    */
    @RequestMapping(value ="updateUserBaseInfo",method = RequestMethod.POST)
    public @ResponseBody BaseResult updateUserBaseInfo(@RequestBody UserBaseinfoVo vo) throws Exception {
    	try {
    		redisService.tokenValidate(vo.getToken());
    		logger.info("EK运营系统日志：方法名[{}]参数[{}]操作时间[{}]操作人[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),vo,DateUtils.getDateTime(),redisService.get(vo.getToken()));
    		userBaseinfoService.updateUserBaseInfo(vo);
    		return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG);
    	} catch (Exception e) {
    		logger.error("ERROR EK运营系统日志：方法名[{}]报错[{}] 参数[{}] 报错时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),e.getMessage(),vo,DateUtils.getDateTime());
    		return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
    	}
    }
}
