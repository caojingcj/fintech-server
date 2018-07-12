package com.fintech.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
import com.fintech.util.HttpClient;
import com.fintech.util.HttpGetUtil;
import com.fintech.util.StringUtil;
import com.fintech.util.enumerator.ConstantInterface;
import com.fintech.util.sign.ParamSignUtils;

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
//        if (jsonObject.isNullObject()) {
//            throw new Exception(ConstantInterface.AppValidateConfig.OrderValidate.ORDER_200005.toString());
//        }
        openid = jsonObject.get("openid").toString();
//         openid = "as65d4a65dw56ad48q6d4";
        parms.put("openId", openid);
        String token = redisService.get(openid);
        logger.info("EK 微信授权 openid[{}]token[{}]操作时间[{}]", openid, token==null?"第一次登陆 NULL":token, DateUtils.getDateTime());
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
                OrderBaseinfo baseinfo = orderBaseinfoMapper.selectByPrimaryKeyWx(mapOrder);
                if (baseinfo != null) {
                    logger.info("EK 有录入定单 baseinfo[{}]操作时间[{}]", baseinfo, DateUtils.getDateTime());
                    LogOrder logOrder = logOrderMapper.selectByPrimaryKeyStatus(baseinfo.getOrderId());
                    parms.put("orderStatus", logOrder.getOrderStatus());
                    parms.put("orderOperation", logOrder.getOrderOperation());
                    parms.put("orderId", baseinfo.getOrderId());
                }
            }
        }
        parms.put("loginFlag", loginFlag);
        return parms;
    }
    
    
    @Override
    public Map<String, Object> wxOrderList(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
//        if (jsonObject.isNullObject()) {
//            throw new Exception(ConstantInterface.AppValidateConfig.OrderValidate.ORDER_200005.toString());
//        }
        openid = jsonObject.get("openid").toString();
//         openid = "as65d4a65dw56ad48q6d4";
        parms.put("openId", openid);
        String token = redisService.get(openid);
        logger.info("EK 微信授权 openid[{}]token[{}]操作时间[{}]", openid, token==null?"第一次登陆 NULL":token, DateUtils.getDateTime());
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
                OrderBaseinfo baseinfo = orderBaseinfoMapper.selectByPrimaryKeyWx(mapOrder);
                if (baseinfo != null) {
                    logger.info("EK 有录入定单 baseinfo[{}]操作时间[{}]", baseinfo, DateUtils.getDateTime());
                    LogOrder logOrder = logOrderMapper.selectByPrimaryKeyStatus(baseinfo.getOrderId());
                    parms.put("orderStatus", logOrder.getOrderStatus());
                    parms.put("orderOperation", logOrder.getOrderOperation());
                    parms.put("orderId", baseinfo.getOrderId());
                }
            }
        }
        parms.put("loginFlag", loginFlag);
        return parms;
    }
    
    /* (非 Javadoc) 
    * <p>Title: wxSignature</p> 
    * <p>Description: </p> 
    * @return 
    * @see com.fintech.service.WxApiService#wxSignature() 
    */
    @Override
    public Map<String, Object> wxJSSignature() throws Exception{
        String jsapi_ticket=redisService.get("WEIXIN_TICKET");
        String noncestr = UUID.randomUUID().toString().replace("-", "").substring(0, 16);//随机字符串
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);//时间戳
        String str = "jsapi_ticket="+jsapi_ticket+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+appConfig.getWEIXIN_API_SIGNATURE_URL();
        String signature=ParamSignUtils.sign(str);
        logger.info("EK 微信授权 获取JS-SDK使用权限签名算法 结果：【appId[{}]timestamp[{}]nonceStr[{}]signature[{}]】 操作时间[{}]", appConfig.getWEIXIN_API_APPID(),timestamp,noncestr,signature, DateUtils.getDateTime());
        Map<String, Object>map=new HashMap<>();
        map.put("appId", appConfig.getWEIXIN_API_APPID());
        map.put("timestamp", timestamp);
        map.put("noncestr", noncestr);
        map.put("signature", signature);
        map.put("str", str);
        map.put("jsapi_ticket", jsapi_ticket);
        return map;
    }
}
