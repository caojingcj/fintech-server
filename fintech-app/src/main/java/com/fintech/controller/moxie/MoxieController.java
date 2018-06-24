package com.fintech.controller.moxie;

import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fintech.common.properties.AppConfig;
import com.fintech.model.LogMoxieinfo;
import com.fintech.model.LogOrder;
import com.fintech.service.LogMoxieinfoService;
import com.fintech.service.LogOrderService;
import com.fintech.util.DateUtils;
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
    private LogMoxieinfoService logMoxieinfoService;
@Autowired
private LogOrderService logOrderService;

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
    @RequestMapping("toMoxieCarrierH5")
    public ModelAndView toMoxieCarrierH5(String orderId,String mobile,String idCard,String name) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("mobile", mobile);
        mav.addObject("name", name);
        mav.addObject("idCard", idCard);
        try {
            logger.info("EK魔蝎日志 H5参数【姓名[{}手机号[{}]身份证[{}]]】>方法名[{}]操作时间[{}]",name,mobile,idCard,Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
            logOrderService.insertSelective(new LogOrder(orderId, String.valueOf(03), String.valueOf(00), null));        
            String loginParams = URLEncoder.encode("{\"phone\":\"" + mobile + "\",\"name\":\"" +
                            name + "\",\"idcard\":\"" + idCard + "\"}", "UTF-8");
                    mav.setViewName("redirect:https://api.51datakey.com/h5/importV3/index.html#/carrier" +
                            "?apiKey=" + appConfig.getMOXIE_APIKEY() +//魔蝎分配给合作机构的Key
                            "&userId=" + mobile +//接入方业务系统的标识用户的ID
                            "&quitOnLoginDone=YES" +//登录成功时跳转到backUrl
                            "&backUrl=" + appConfig.getMOXIE_BACKURL() +//任务成功后的回调地址
                            "&themeColor=2196F3" +//主题颜色
                            "&cacheDisable=YES" +//传递该参数为'YES'时，禁止缓存读取和写入。默认不传则会使用缓存
                            "&loginParams=" + loginParams);
        } catch (Exception e) {
            logger.error("EK ERROR [{}]魔蝎日志 H5参数【姓名[{}手机号[{}]身份证[{}]]】>方法名[{}]操作时间[{}]",e.getMessage(),name,mobile,idCard,Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
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
    @RequestMapping("getMXPresentation")
    public @ResponseBody BaseResult toMoxieCarrierH5(LogMoxieinfo record) {
        logger.info("EK 获取魔蝎报告[{}]方法名[{}]操作时间[{}]",record,Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
        try {
            logMoxieinfoService.insertSelective(record);
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG);
        } catch (Exception e) {
            logger.error("ERROR EK参数[{}] 报错[{}] 方法名[{}]报错时间[{}]",record,e.getMessage(),Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
        }
        
    }
}
