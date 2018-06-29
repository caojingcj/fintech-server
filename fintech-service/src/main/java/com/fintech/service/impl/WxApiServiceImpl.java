package com.fintech.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.dao.LogOrderMapper;
import com.fintech.dao.OrderBaseinfoMapper;
import com.fintech.model.LogOrder;
import com.fintech.model.OrderBaseinfo;
import com.fintech.service.RedisService;
import com.fintech.service.WxApiService;
import com.fintech.util.DateUtils;
import com.fintech.util.HttpGetUtil;
import com.fintech.util.StringUtil;
import com.fintech.util.enumerator.ConstantInterface;
import com.google.gson.Gson;

import net.sf.json.JSONObject;

@Service
public class WxApiServiceImpl implements WxApiService {
    private static final Logger logger = LoggerFactory.getLogger(WxApiServiceImpl.class);

    @Autowired
    private RedisService redisService;
    @Autowired
    private OrderBaseinfoMapper orderBaseinfoMapper;
    @Autowired
    private LogOrderMapper logOrderMapper;

    @Override
    public Map<String, Object> wxOpenId(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userAgent = request.getHeader("user-agent").toLowerCase();
        String openid="";
        boolean loginFlag=false;
//        if(userAgent.indexOf("micromessenger")==-1){//微信客户端
//            throw new Exception(ConstantInterface.AppValidateConfig.OrderValidate.ORDER_200005.toString());
//        }
        Map<String, Object>parms=new HashMap<>();
            response.setContentType("text/html");
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            String code = request.getParameter("code");// 获取code
            Map<String, String> weixinMap = new HashMap<>();
            weixinMap.put("secret", "1ac45a12a035d3a279b69d7f0e58aaa5");
            weixinMap.put("appid", "wx4e291d39c10f3c63");
            weixinMap.put("grant_type", "authorization_code");
            weixinMap.put("code", code);
//            String result = HttpGetUtil.httpRequestToString("https://api.weixin.qq.com/sns/oauth2/access_token", weixinMap);
//            JSONObject jsonObject = JSONObject.fromObject(result);
//            if(jsonObject.isNullObject()) {
//                throw new Exception(ConstantInterface.AppValidateConfig.OrderValidate.ORDER_200005.toString());
//            }
//            openid = jsonObject.get("openid").toString();
            openid = "as65d4a65dw56ad48q6d4";
            parms.put("openId", openid);
            String token=redisService.get(openid);
            logger.info("EK 微信授权 openid[{}]token[{}]操作时间[{}]",openid,token,DateUtils.getDateTime());
            if(!StringUtil.isEmpty(token)) {
                loginFlag=true;
                logger.info("EK 微信授权 有 token[{}]操作时间[{}]",token,DateUtils.getDateTime());
                parms.put("token", token);
                String mobile= redisService.get(token);
                if(!StringUtil.isEmpty(mobile)) {
                    logger.info("EK 微信授权 有 mobile[{}]操作时间[{}]",mobile,DateUtils.getDateTime());
                    parms.put("mobile", mobile);
                    Map<String, Object>mapOrder=new HashMap<>();
                    mapOrder.put("custCellphone", mobile);
                    mapOrder.put("orderStatus", ConstantInterface.Enum.OrderStatus.ORDER_STATUS00.getKey());
                    OrderBaseinfo baseinfo=orderBaseinfoMapper.selectByPrimaryKeySelective(mapOrder);
                    if(baseinfo!=null) {
                        logger.info("EK 有录入定单 baseinfo[{}]操作时间[{}]",baseinfo,DateUtils.getDateTime());
                        LogOrder logOrder= logOrderMapper.selectByPrimaryKeyStatus(baseinfo.getOrderId());
                        parms.put("orderStatus", logOrder.getOrderStatus());
                        parms.put("orderOperation", logOrder.getOrderOperation());
                    }
                }
            }
            parms.put("loginFlag", loginFlag);
        return parms;
    }


}
