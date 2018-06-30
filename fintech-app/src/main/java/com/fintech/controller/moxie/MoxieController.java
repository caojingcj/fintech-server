package com.fintech.controller.moxie;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fintech.common.properties.AppConfig;
import com.fintech.model.LogMoxieinfo;
import com.fintech.model.LogOrder;
import com.fintech.model.vo.moxie.BackMoxieTaskSubmitVo;
import com.fintech.service.LogOrderService;
import com.fintech.service.MoxieService;
import com.fintech.service.RedisService;
import com.fintech.util.DateUtils;
import com.fintech.util.enumerator.ConstantInterface;
import com.fintech.util.result.BaseResult;
import com.fintech.util.result.ResultUtils;


/**   
* @Title: MoxieController.java 
* @Package com.fintech.controller.moxie 
* @author qierkang xyqierkang@163.com   
* @date 2018年6月24日 上午2:20:43  
* @Description: TODO[ 魔蝎控制类 ]
*/
@Controller
@RequestMapping("app/moxie")
public class MoxieController {
    private static final Logger logger = LoggerFactory.getLogger(MoxieController.class);

    @Autowired
    private AppConfig appConfig;
    @Autowired
    private MoxieService moxieService;
	@Autowired
	private LogOrderService logOrderService;
	@Autowired
	private RedisService redisService;

    /** 
    * @Title: MoxieController.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年6月24日 上午4:01:06  
    * @param @param mobile
    * @param @param idCard
    * @param @param name
    * @param @return    设定文件 
    * @Description: TODO[ 魔蝎H5 ]
    * @throws 
    */
	@RequestMapping(value = "toMoxieCarrierH5",method = RequestMethod.GET)
    public @ResponseBody Object toMoxieCarrierH5(String orderId,String mobile,String idCard,String name,String token) {
        ModelAndView mav = new ModelAndView();
        try {
        	redisService.tokenValidate(token);
        	mav.addObject("mobile", mobile);
        	mav.addObject("name", name);
        	mav.addObject("idCard", idCard);
            logger.info("EK魔蝎日志 H5参数【定单号[{}]姓名[{}]手机号[{}]身份证[{}]】>方法名[{}]操作时间[{}]",orderId,name,mobile,idCard,Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
            String loginParams = URLEncoder.encode("{\"phone\":\"" + mobile + "\",\"name\":\"" +
                            name + "\",\"idcard\":\"" + idCard + "\"}", "UTF-8");
            String moxieUrl="https://api.51datakey.com/h5/importV3/index.html#/carrier?apiKey="+appConfig.getMOXIE_APIKEY()+"&userId="+orderId+"&quitOnLoginDone=YES&backUrl="+appConfig.getMOXIE_BACKURL()+"&themeColor=2196F3&cacheDisable=YES&loginParams="+loginParams;
            logOrderService.insertSelective(new LogOrder(orderId, ConstantInterface.Enum.OrderLogStatus.ORDER_STATUS03.getKey(), ConstantInterface.Enum.OrderStatus.ORDER_STATUS00.getKey(), null));
//                    mav.setViewName("redirect:https://api.51datakey.com/h5/importV3/index.html#/carrier" +
//                            "?apiKey=" + appConfig.getMOXIE_APIKEY() +//魔蝎分配给合作机构的Key
//                            "&userId=" + mobile +//接入方业务系统的标识用户的ID
//                            "&quitOnLoginDone=YES" +//登录成功时跳转到backUrl
//                            "&backUrl=" + appConfig.getMOXIE_BACKURL() +//任务成功后的回调地址
//                            "&themeColor=2196F3" +//主题颜色
//                            "&cacheDisable=YES" +//传递该参数为'YES'时，禁止缓存读取和写入。默认不传则会使用缓存
//                            "&loginParams=" + loginParams);
                    return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG,moxieUrl);
        } catch (Exception e) {
            logger.error("EK ERROR [{}]魔蝎日志 H5参数【定单号[{}]姓名[{}手机号[{}]身份证[{}]]】>方法名[{}]操作时间[{}]",e.getMessage(),orderId,name,mobile,idCard,Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
        }
        return mav;
    }
    
    /** 
    * @Title: MoxieController.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年6月24日 上午3:52:29  
    * @param @param record    设定文件 
    * @Description: TODO[ 获取魔蝎报告 ]
    * @throws 
    */
    @RequestMapping(value = "getMXPresentation",method = RequestMethod.GET)
    public @ResponseBody BaseResult toMoxieCarrierH5(LogMoxieinfo record) {
        logger.info("EK 获取魔蝎报告[{}]方法名[{}]操作时间[{}]",record,Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
        try {
            moxieService.insertSelective(record);
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG);
        } catch (Exception e) {
            logger.error("ERROR EK参数[{}] 报错[{}] 方法名[{}]报错时间[{}]",record,e.getMessage(),Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
        }
    }
    
    @RequestMapping(value = "backMoxieTaskSubmit",method = RequestMethod.POST)
    public @ResponseBody Object backMoxieTaskSubmit(@RequestBody BackMoxieTaskSubmitVo vo) {
        logger.info("EK 接到魔蝎回调：任务创建通知[{}]方法名[{}]操作时间[{}]",vo,Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
        try {
            moxieService.backMoxieTaskSubmit(vo);
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG);
        } catch (Exception e) {
            logger.error("ERROR EK参数[{}] 报错[{}] 方法名[{}]报错时间[{}]",vo,e.getMessage(),Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
        }
    }
    
    @RequestMapping(value = "resultMoxie",method = RequestMethod.GET)
    public @ResponseBody BaseResult resultMoxie(String userId,String account) {
        logger.info("EK 获取魔蝎报告返回成功[{}]方法名[{}]操作时间[{}]",userId,account,Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
        try {
            Map<String, Object>res=new HashMap<>();
            res.put("orderId", userId);
            res.put("mobile", account);
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG,res);
        } catch (Exception e) {
            logger.error("ERROR EK参数[{}] 报错[{}] 方法名[{}]报错时间[{}]",userId,account,e.getMessage(),Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
        }
    }
}
