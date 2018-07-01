package com.fintech.controller.moxie;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fintech.common.properties.AppConfig;
import com.fintech.dao.OrderBaseinfoMapper;
import com.fintech.model.LogMoxieinfo;
import com.fintech.model.LogOrder;
import com.fintech.model.OrderBaseinfo;
import com.fintech.model.vo.moxie.BackMoxieTaskSubmitVo;
import com.fintech.service.LogOrderService;
import com.fintech.service.MoxieService;
import com.fintech.service.RedisService;
import com.fintech.util.DateUtils;
import com.fintech.util.enumerator.ConstantInterface;
import com.fintech.util.result.BaseResult;
import com.fintech.util.result.ResultUtils;
import com.google.gson.Gson;

import net.sf.json.JSONObject;


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
	@Autowired
	private OrderBaseinfoMapper orderBaseinfoMapper;

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
    public @ResponseBody Object toMoxieCarrierH5(String orderId,String token) {
        try {
            redisService.tokenValidate(token);
            redisService.set(orderId,"999999");
            OrderBaseinfo baseinfo=orderBaseinfoMapper.selectByPrimaryKey(orderId);
            logger.info("EK魔蝎日志 H5参数【定单号[{}]token[{}]】>方法名[{}]操作时间[{}]",orderId,token,Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
            String loginParams = URLEncoder.encode("{\"phone\":\"" + baseinfo.getCustCellphone() + "\",\"name\":\"" +
                    baseinfo.getCustRealname() + "\",\"idcard\":\"" + baseinfo.getCustIdCardNo() + "\"}", "UTF-8");
            String moxieUrl="https://api.51datakey.com/h5/importV3/index.html#/carrier?apiKey="+appConfig.getMOXIE_APIKEY()+"&userId="+orderId+"&quitOnLoginDone=YES&goBackEnable=YES&backUrl="+appConfig.getMOXIE_BACKURL()+"&themeColor=2196F3&cacheDisable=YES&loginParams="+loginParams;
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG,moxieUrl);
        } catch (Exception e) {
            logger.error("EK ERROR [{}]魔蝎日志 H5参数【定单号[{}]]】>方法名[{}]操作时间[{}]",e.getMessage(),orderId,Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
        }
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
        logger.info("EK 接到魔蝎回调任务创建通知 参数[{}]方法名[{}]操作时间[{}]",vo,Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
        try {
            moxieService.backMoxieTaskSubmit(vo);
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG);
        } catch (Exception e) {
            logger.error("ERROR EK参数[{}] 报错[{}] 方法名[{}]报错时间[{}]",vo,e.getMessage(),Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
        }
    }
    
    @RequestMapping(value = "resultMoxie",method = RequestMethod.GET)
    public @ResponseBody Object resultMoxie(HttpServletRequest request) {
        logger.info("EK 获取魔蝎报告返回成功[userId[{}]]方法名[{}]操作时间[{}]",request.getParameter("userId"),Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
        try {
            String orderId=request.getParameter("userId");
            if(orderId!=null) {
                moxieService.resultMoxie(orderId);
            }
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG,redisService.get(orderId));
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("ERROR EK 魔蝎报告报错[{}] 方法名[{}]报错时间[{}]", e.getMessage(),Thread.currentThread().getStackTrace()[1].getMethodName(), DateUtils.getDateTime());
                return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
            }
    }
}
