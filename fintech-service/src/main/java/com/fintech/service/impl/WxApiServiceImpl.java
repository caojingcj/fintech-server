package com.fintech.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.common.properties.AppConfig;
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
    @Autowired
    private AppConfig appConfig;

    @Override
    public Map<String, Object> wxOpenId(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userAgent = request.getHeader("user-agent").toLowerCase();
        String openid = "";
        boolean loginFlag = false;
        Map<String, Object> parms = new HashMap<>();
        response.setContentType(ConstantInterface.Enum.CONTENT_TYPE.CONTENT_TYPE_TEXTHTML.getValue());
        request.setCharacterEncoding(ConstantInterface.Enum.ENCODING.ENCODING_UTF8.getValue());
        response.setCharacterEncoding(ConstantInterface.Enum.ENCODING.ENCODING_UTF8.getValue());
        String code = request.getParameter("code");// 获取code
        Map<String, String> weixinMap = new HashMap<>();
        weixinMap.put("secret", appConfig.getWEIXIN_API_SECRET());
        weixinMap.put("appid", appConfig.getWEIXIN_API_APPID());
        weixinMap.put("grant_type", appConfig.getWEIXIN_API_GRANT_TYPE());
        weixinMap.put("code", code);
        String result = HttpGetUtil.httpRequestToString("https://api.weixin.qq.com/sns/oauth2/access_token", weixinMap);
        JSONObject jsonObject = JSONObject.fromObject(result);
        if (jsonObject.isNullObject()) {
            throw new Exception(ConstantInterface.AppValidateConfig.OrderValidate.ORDER_200005.toString());
        }
        openid = jsonObject.get("openid").toString();
        // openid = "as65d4a65dw56ad48q6d4";
        parms.put("openId", openid);
        String token = redisService.get(openid);
        logger.info("EK 微信授权 openid[{}]token[{}]操作时间[{}]", openid, token, DateUtils.getDateTime());
        if (!StringUtil.isEmpty(token)) {
            loginFlag = true;
            logger.info("EK 微信授权 有 token[{}]操作时间[{}]", token, DateUtils.getDateTime());
            parms.put("token", token);
            String mobile = redisService.get(token);
            if (!StringUtil.isEmpty(mobile)) {
                logger.info("EK 微信授权 有 mobile[{}]操作时间[{}]", mobile, DateUtils.getDateTime());
                parms.put("mobile", mobile);
                Map<String, Object> mapOrder = new HashMap<>();
                mapOrder.put("custCellphone", mobile);
                mapOrder.put("orderStatus", ConstantInterface.Enum.OrderStatus.ORDER_STATUS00.getKey());
                OrderBaseinfo baseinfo = orderBaseinfoMapper.selectByPrimaryKeySelective(mapOrder);
                if (baseinfo != null) {
                    logger.info("EK 有录入定单 baseinfo[{}]操作时间[{}]", baseinfo, DateUtils.getDateTime());
                    LogOrder logOrder = logOrderMapper.selectByPrimaryKeyStatus(baseinfo.getOrderId());
                    parms.put("orderStatus", logOrder.getOrderStatus());
                    parms.put("orderOperation", logOrder.getOrderOperation());
                }
            }
        }
        parms.put("loginFlag", loginFlag);
        return parms;
    }
}
