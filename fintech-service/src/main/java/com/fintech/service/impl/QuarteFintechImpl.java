package com.fintech.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fintech.common.properties.AppConfig;
import com.fintech.service.QuarteFintechService;
import com.fintech.service.RedisService;
import com.fintech.util.DateUtils;
import com.fintech.util.HttpClient;
import com.fintech.util.StringUtil;

import net.sf.json.JSONObject;

@Service
public class QuarteFintechImpl implements QuarteFintechService {
    private static final Logger logger = LoggerFactory.getLogger(QuarteFintechImpl.class);
    @Autowired
    private RedisService redisService;
    @Autowired
    private AppConfig appConfig;

    @Override
    @Scheduled(fixedRate = 1000 * 60 * 59 * 2) // 服务器启动时执行一次，之后每隔一个小时59分执行一次。
    public void wxAuthentication() throws Exception {
        try {
            String WEIXIN_API_ACCESS_TOKEN = HttpClient.get(appConfig.getWEIXIN_API_ACCESS_TOKEN_URL().replace("{appid}", appConfig.getWEIXIN_API_APPID()).replace("{secret}", appConfig.getWEIXIN_API_SECRET()));
            if(StringUtil.isEmpty(WEIXIN_API_ACCESS_TOKEN)) {
                logger.error("EK 获取微信token失败[{}]，操作时间[{}]",WEIXIN_API_ACCESS_TOKEN,DateUtils.getDateTime());
                return;
            }
            JSONObject token= JSONObject.fromObject(WEIXIN_API_ACCESS_TOKEN);
            String WEIXIN_API_JSAPI = HttpClient.get(appConfig.getWEIXIN_API_ACCESS_JSAPI_URL().replace("{accessToken}", token.get("access_token").toString()));
            JSONObject ticket= JSONObject.fromObject(WEIXIN_API_JSAPI);
            logger.info("EK 获取微信授权TOKEN[{}]>>>TICKET[{}]操作时间[{}]", token.get("access_token"), ticket.get("ticket"),
                    DateUtils.getDateTime());
            redisService.set("WEIXIN_TICKET", token.get("access_token").toString());
            redisService.set("WEIXIN_ACCESS_TOKEN", ticket.get("ticket").toString());
        } catch (Exception e) {
            logger.error("EK 获取微信token异常[{}]，操作时间[{}]",e.getMessage(),DateUtils.getDateTime());
        }
    }

}
