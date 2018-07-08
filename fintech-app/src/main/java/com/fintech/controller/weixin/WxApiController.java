package com.fintech.controller.weixin;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fintech.common.properties.AppConfig;
import com.fintech.service.RedisService;
import com.fintech.service.WxApiService;
import com.fintech.util.DateUtils;
import com.fintech.util.enumerator.ConstantInterface;
import com.fintech.util.result.ResultUtils;
import com.fintech.util.sign.ParamSignUtils;
import com.google.gson.Gson;

/**   
* @Title: WxApiController.java 
* @Package com.fintech.controller.weixin 
* @author qierkang xyqierkang@163.com   
* @date 2018年6月29日 下午5:53:00  
* @Description: TODO[ 用一句话描述该文件做什么 ]
*/
@Controller
@RequestMapping("app/weixin")
public class WxApiController {
    private static final Logger logger = LoggerFactory.getLogger(WxApiController.class);

    @Autowired
    private WxApiService wxApiService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private AppConfig appConfig;
    /** 
    * @Title: WxApiController.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年6月29日 下午5:53:01  
    * @param @param request
    * @param @param response    设定文件 
    * @Description: TODO[ 这里用一句话描述这个方法的作用 ]
    * @throws 
    */
    @RequestMapping(value = "wxCode",method = RequestMethod.GET)
    public void wxCode(HttpServletRequest request, HttpServletResponse response) {
        logger.info("EK>APP系统日志：我要进件 方法名[{}]操作时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
        try {
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            request.setCharacterEncoding("UTF-8");
            // 这里要将你的授权回调地址处理一下，否则微信识别不了
            String redirect_uri = URLEncoder.encode("https://www.duodingfen.com/fintech-app/app/weixin/wxOpenId", "UTF-8");
            // 简单获取openid的话参数response_type与scope与state参数固定写死即可
            StringBuffer url = new StringBuffer(appConfig.getWEIXIN_API_REDIRECT_URL().replace("{redirect_uri}", redirect_uri));
            response.sendRedirect(url.toString());// 这里请不要使用get请求单纯的将页面跳转到该url即可
//            response.sendRedirect("http://192.168.10.54:8084/app/weixin/wxOpenId");// 这里请不要使用get请求单纯的将页面跳转到该url即可
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /** 
    * @Title: WxApiController.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年7月2日 上午4:43:22  
    * @param @param request
    * @param @param response    设定文件 
    * @Description: TODO[ 这里用一句话描述这个方法的作用 ]
    * @throws 
    */
    @RequestMapping(value = "wxOrderCode",method = RequestMethod.GET)
    public void wxOrderCode(HttpServletRequest request, HttpServletResponse response) {
        try {
            logger.info("EK>APP系统日志：我的订单 方法名[{}]操作时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            request.setCharacterEncoding("UTF-8");
            // 这里要将你的授权回调地址处理一下，否则微信识别不了
            String redirect_uri = URLEncoder.encode("https://www.duodingfen.com/fintech-app/app/weixin/wxOrderList", "UTF-8");
            // 简单获取openid的话参数response_type与scope与state参数固定写死即可
            StringBuffer url = new StringBuffer(appConfig.getWEIXIN_API_REDIRECT_URL().replace("{redirect_uri}", redirect_uri));
            response.sendRedirect(url.toString());// 这里请不要使用get请求单纯的将页面跳转到该url即可
//            response.sendRedirect("http://192.168.10.54:8084/app/weixin/wxOpenId");// 这里请不要使用get请求单纯的将页面跳转到该url即可
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /** 
    * @Title: WxApiController.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年7月2日 上午4:43:20  
    * @param @param request
    * @param @param response    设定文件 
    * @Description: TODO[ 这里用一句话描述这个方法的作用 ]
    * @throws 
    */
    @RequestMapping(value = "wxReturnCode",method = RequestMethod.GET)
    public void wxReturnCode(HttpServletRequest request, HttpServletResponse response) {
        logger.info("EK>APP系统日志：我要还款 方法名[{}]操作时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
        try {
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            request.setCharacterEncoding("UTF-8");
            // 这里要将你的授权回调地址处理一下，否则微信识别不了
            String redirect_uri = URLEncoder.encode("https://www.duodingfen.com/fintech-app/app/weixin/wxOrderReturn", "UTF-8");
            // 简单获取openid的话参数response_type与scope与state参数固定写死即可
            StringBuffer url = new StringBuffer(appConfig.getWEIXIN_API_REDIRECT_URL().replace("{redirect_uri}", redirect_uri));
            response.sendRedirect(url.toString());// 这里请不要使用get请求单纯的将页面跳转到该url即可
//            response.sendRedirect("http://192.168.10.54:8084/app/weixin/wxOpenId");// 这里请不要使用get请求单纯的将页面跳转到该url即可
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 
    * @Title: WxApiController.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年6月29日 下午5:53:03  
    * @param @param request
    * @param @param response    设定文件 
    * @Description: TODO[ 这里用一句话描述这个方法的作用 ]
    * @throws 
    */
    @RequestMapping(value = "wxOpenId",method = RequestMethod.GET)
    public void wxOpenId(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> parms=wxApiService.wxOpenId(request, response);
            Gson gson=new Gson();
            parms.put("pageStatus", ConstantInterface.Enum.ConstantNumber.ONE.getKey());
            String resMap=gson.toJson(parms);
            logger.info("EK>APP系统日志：页面获取的参数[{}]方法名[{}]操作时间[{}]",resMap,Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
            response.sendRedirect(appConfig.getWEIXIN_API_HTML_URL().replace("{resMap}", URLEncoder.encode(resMap, "UTF-8")));
//            response.sendRedirect("http://192.168.10.55:8020/fintech-wechath5/#/waiting?"+URLEncoder.encode(resMap, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("ERROR EK>APP系统日志：报错[{}] 方法名[{}]报错时间[{}]", e.getMessage(),
                    Thread.currentThread().getStackTrace()[1].getMethodName(), DateUtils.getDateTime());
        }
    }
    
    /** 
    * @Title: WxApiController.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年7月2日 上午4:43:27  
    * @param @param request
    * @param @param response    设定文件 
    * @Description: TODO[ 这里用一句话描述这个方法的作用 ]
    * @throws 
    */
    @RequestMapping(value = "wxOrderList",method = RequestMethod.GET)
    public void wxOrderList(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> parms=wxApiService.wxOpenId(request, response);
            Gson gson=new Gson();
            parms.put("pageStatus", ConstantInterface.Enum.ConstantNumber.TOW.getKey());
            String resMap=gson.toJson(parms);
            logger.info("EK>APP系统日志：页面获取的参数[{}]方法名[{}]操作时间[{}]",resMap,Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
            response.sendRedirect(appConfig.getWEIXIN_API_HTML_URL().replace("{resMap}", URLEncoder.encode(resMap, "UTF-8")));
//            response.sendRedirect("http://192.168.10.55:8020/fintech-wechath5/#/waiting?"+URLEncoder.encode(resMap, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("ERROR EK>APP系统日志：报错[{}] 方法名[{}]报错时间[{}]", e.getMessage(),
                    Thread.currentThread().getStackTrace()[1].getMethodName(), DateUtils.getDateTime());
        }
    }
    
    /** 
    * @Title: WxApiController.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年7月2日 上午4:43:29  
    * @param @param request
    * @param @param response    设定文件 
    * @Description: TODO[ 这里用一句话描述这个方法的作用 ]
    * @throws 
    */
    @RequestMapping(value = "wxOrderReturn",method = RequestMethod.GET)
    public void wxOrderReturn(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> parms=wxApiService.wxOpenId(request, response);
            Gson gson=new Gson();
            parms.put("pageStatus", ConstantInterface.Enum.ConstantNumber.THREE.getKey());
            String resMap=gson.toJson(parms);
            logger.info("EK>APP系统日志：页面获取的参数[{}]方法名[{}]操作时间[{}]",resMap,Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
            response.sendRedirect(appConfig.getWEIXIN_API_HTML_URL().replace("{resMap}", URLEncoder.encode(resMap, "UTF-8")));
//            response.sendRedirect("http://192.168.10.55:8020/fintech-wechath5/#/waiting?"+URLEncoder.encode(resMap, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("ERROR EK>APP系统日志： 报错[{}] 方法名[{}]报错时间[{}]", e.getMessage(),
                    Thread.currentThread().getStackTrace()[1].getMethodName(), DateUtils.getDateTime());
        }
    }
    

    /** 
    * @Title: WxApiController.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年7月2日 上午4:43:31  
    * @param @param token
    * @param @return    设定文件 
    * @Description: TODO[ 这里用一句话描述这个方法的作用 ]
    * @throws 
    */
    @RequestMapping(value = "wxJsTicket",method = RequestMethod.GET)
    public @ResponseBody Object wxJsTicket(String token) {
        try {
            redisService.tokenValidate(token);
            logger.info("EK>APP系统日志： 客户订单列表[token{}]方法名[{}]操作时间[{}]",token,Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG,redisService.get("WEIXIN_API_JSAPI"));
        } catch (Exception e) {
            logger.error("ERROR EK>APP系统日志：参数[{}] 报错[{}] 方法名[{}]报错时间[{}]", token,e.getMessage(),
                    Thread.currentThread().getStackTrace()[1].getMethodName(), DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE, e.getMessage());
        }
    }
    
    /** 
    * @Title: WxApiController.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年7月1日 上午12:31:19  
    * @param @param token
    * @param @return    设定文件 
    * @Description: TODO[ 获取JS-SDK使用权限签名算法 ]
    * @throws 
    */
    @RequestMapping(value = "wxJSSignature",method = RequestMethod.GET)
    public @ResponseBody Object wxJSSignature(String token) {
        logger.info("EK>APP系统日志： 微信授权 获取JS-SDK使用权限签名算法方法名[{}]操作时间[{}]",token,Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
        try {
            redisService.tokenValidate(token);
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG,wxApiService.wxJSSignature());
        } catch (Exception e) {
            logger.error("ERROR EK>APP系统日志：参数[{}] 报错[{}] 方法名[{}]报错时间[{}]", token,e.getMessage(),
                    Thread.currentThread().getStackTrace()[1].getMethodName(), DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE, e.getMessage());
        }
    }
}
