package com.fintech.controller.weixin;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fintech.service.WxApiService;
import com.fintech.util.DateUtils;
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
        logger.info("EK 获取微信授权 方法名[{}]操作时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
        try {
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            request.setCharacterEncoding("UTF-8");
            // 这里要将你的授权回调地址处理一下，否则微信识别不了
            String redirect_uri = URLEncoder.encode("https://www.duodingfen.com/fintech-app/app/weixin/wxOpenId", "UTF-8");
            // 简单获取openid的话参数response_type与scope与state参数固定写死即可
            StringBuffer url = new StringBuffer(
                    "https://open.weixin.qq.com/connect/oauth2/authorize?redirect_uri=" + redirect_uri + "&appid=wx4e291d39c10f3c63&response_type=code&scope=snsapi_base&state=1#wechat_redirect");
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
            String resMap=gson.toJson(parms);
            logger.info("EK 页面获取的参数[{}]方法名[{}]操作时间[{}]",resMap,Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
            response.sendRedirect("https://www.duodingfen.com/fintech-wechat/#/waiting?"+URLEncoder.encode(resMap, "UTF-8"));
//            response.sendRedirect("http://192.168.10.55:8020/fintech-wechath5/#/waiting?"+URLEncoder.encode(resMap, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("ERROR EK 报错[{}] 方法名[{}]报错时间[{}]", e.getMessage(),
                    Thread.currentThread().getStackTrace()[1].getMethodName(), DateUtils.getDateTime());
        }
    }
    
    public static void main(String[] args) throws UnsupportedEncodingException {
        Gson gson=new Gson();
        Map<String, Object>map=new HashMap<>();
        map.put("dddd", "13213");
        System.out.println(URLEncoder.encode(gson.toJson(map).toString(), "UTF-8"));
    }
}
