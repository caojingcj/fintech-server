package com.fintech.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.service.RedisService;
import com.fintech.service.WxApiService;
import com.fintech.util.HttpGetUtil;
import com.fintech.util.StringUtil;
import com.fintech.util.enumerator.ConstantInterface;
import com.fintech.util.sign.TokenUtil;

import net.sf.json.JSONObject;

@Service
public class WxApiServiceImpl implements WxApiService {

    @Autowired
    private RedisService redisService;

    @Override
    public String wxOpenId(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userAgent = request.getHeader("user-agent").toLowerCase();
        String openid="";
//        if(userAgent.indexOf("micromessenger")==-1){//微信客户端
//            throw new Exception(ConstantInterface.AppValidateConfig.OrderValidate.ORDER_200005.toString());
//        }
            response.setContentType("text/html");
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
            System.out.println("微信返回："+jsonObject);
//            if(jsonObject.isNullObject()) {
//                throw new Exception(ConstantInterface.AppValidateConfig.OrderValidate.ORDER_200005.toString());
//            }
            openid = jsonObject.get("openid").toString();
            String token=redisService.get(openid);
            if(!StringUtil.isEmpty(token)) {
                String mobile= redisService.get(token);
                System.out.println("手机号"+mobile);
            }
        return openid;
    }


}
