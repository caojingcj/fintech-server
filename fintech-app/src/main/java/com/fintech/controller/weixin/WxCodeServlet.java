package com.fintech.controller.weixin;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fintech.util.DateUtils;

@WebServlet(name = "wxServlet", urlPatterns = { "/wxServlet" })
public class WxCodeServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(WxCodeServlet.class);

    /** 
    * @Fields serialVersionUID : TODO[ 用一句话描述这个变量表示什么 ] 
    */ 
    private static final long serialVersionUID = -7210422716867562416L;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("EK 获取微信授权 方法名[{}]操作时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        // 这里要将你的授权回调地址处理一下，否则微信识别不了
        String redirect_uri = URLEncoder.encode("https://www.duodingfen.com/fintech-app/wxOpenIdServlet", "UTF-8");
        // 简单获取openid的话参数response_type与scope与state参数固定写死即可
        StringBuffer url = new StringBuffer(
                "https://open.weixin.qq.com/connect/oauth2/authorize?redirect_uri=" + redirect_uri + "&appid=wx4e291d39c10f3c63&response_type=code&scope=snsapi_base&state=1#wechat_redirect");
        response.sendRedirect(url.toString());// 这里请不要使用get请求单纯的将页面跳转到该url即可
    }

}
