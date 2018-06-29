package com.fintech.controller.weixin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fintech.util.DateUtils;

import net.sf.json.JSONObject;
@WebServlet(name = "wxOpenIdServlet", urlPatterns = { "/wxOpenIdServlet" })
public class WxOpenIdServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(WxOpenIdServlet.class);

    /** 
    * @Fields serialVersionUID : TODO[ 用一句话描述这个变量表示什么 ] 
    */
    private static final long serialVersionUID = 2322653404489406782L;

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        logger.info("EK servlet获取微信openId方法名[{}]操作时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
        response.setContentType("text/html");
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            String code = request.getParameter("code");// 获取code
            Map<String, String> params = new HashMap<>();
            params.put("secret", "1ac45a12a035d3a279b69d7f0e58aaa5");
            params.put("appid", "wx4e291d39c10f3c63");
            params.put("grant_type", "authorization_code");
            params.put("code", code);
            String result = HttpGetUtil.httpRequestToString("https://api.weixin.qq.com/sns/oauth2/access_token", params);
            JSONObject jsonObject = JSONObject.fromObject(result);
            String openid = jsonObject.get("openid").toString();
            logger.info("EK 获取微信openId[{}]方法名[{}]操作时间[{}]",openid,Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
