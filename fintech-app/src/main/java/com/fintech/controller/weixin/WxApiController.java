package com.fintech.controller.weixin;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fintech.service.WxApiService;
import com.fintech.util.DateUtils;
import com.fintech.util.StringUtil;
import com.fintech.util.result.ResultUtils;

@Controller
@RequestMapping("app/weixin")
public class WxApiController {
    private static final Logger logger = LoggerFactory.getLogger(WxApiController.class);

    @Autowired
    private WxApiService wxApiService;
    @RequestMapping(value = "wxCode",method = RequestMethod.GET)
    public void wxCode(HttpServletRequest request, HttpServletResponse response) {
        logger.info("EK 获取微信授权 方法名[{}]操作时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
        try {
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            request.setCharacterEncoding("UTF-8");
            // 这里要将你的授权回调地址处理一下，否则微信识别不了
            String redirect_uri = URLEncoder.encode("https://www.duodingfen.com/fintech-app/app/weixin/wxOpenId?callback=wxbacnk", "UTF-8");
            // 简单获取openid的话参数response_type与scope与state参数固定写死即可
            StringBuffer url = new StringBuffer(
                    "https://open.weixin.qq.com/connect/oauth2/authorize?redirect_uri=" + redirect_uri + "&appid=wx4e291d39c10f3c63&response_type=code&scope=snsapi_base&state=1#wechat_redirect");
            response.sendRedirect(url.toString());// 这里请不要使用get请求单纯的将页面跳转到该url即可
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "wxOpenId",method = RequestMethod.GET)
    public @ResponseBody Object wxOpenId(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            request.setCharacterEncoding("UTF-8");
            String redirect_uri = URLEncoder.encode("https://www.duodingfen.com/fintech-app/app/weixin/wxOpenId?callback=wxback", "UTF-8");
            StringBuffer url = new StringBuffer(
                    "https://open.weixin.qq.com/connect/oauth2/authorize?redirect_uri=" + redirect_uri + "&appid=wx4e291d39c10f3c63&response_type=code&scope=snsapi_base&state=1#wechat_redirect");
            String callback = request.getParameter("callback");// 获取code
            System.out.println("回调的连接"+url);
            if(StringUtil.isEmpty(callback)) {
                response.sendRedirect(url.toString());// 这里请不要使用get请求单纯的将页面跳转到该url即可
            }
            logger.info("EK wxOpenId获取微信openId 方法名[{}]操作时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
           String openId= wxApiService.wxOpenId(request, response);
            logger.info("EK 获取微信openId[{}]方法名[{}]操作时间[{}]",openId,Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG,openId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("ERROR EK 报错[{}] 方法名[{}]报错时间[{}]", e.getMessage(),
                    Thread.currentThread().getStackTrace()[1].getMethodName(), DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE, e.getMessage());
        }
    }
}
