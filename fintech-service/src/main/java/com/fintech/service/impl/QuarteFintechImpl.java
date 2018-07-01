package com.fintech.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fintech.common.properties.AppConfig;
import com.fintech.dao.CustBaseinfoMapper;
import com.fintech.dao.LogOrderMapper;
import com.fintech.dao.OrderBaseinfoMapper;
import com.fintech.model.CustBaseinfo;
import com.fintech.model.LogOrder;
import com.fintech.model.OrderBaseinfo;
import com.fintech.service.QuarteFintechService;
import com.fintech.service.RedisService;
import com.fintech.service.ReturnPlanService;
import com.fintech.util.DateUtils;
import com.fintech.util.HttpClient;
import com.fintech.util.StringUtil;
import com.fintech.util.enumerator.ConstantInterface;
import com.fintech.xcpt.FintechException;

import net.sf.json.JSONObject;

/**   
* @Title: QuarteFintechImpl.java 
* @Package com.fintech.service.impl 
* @author qierkang xyqierkang@163.com   
* @date 2018年7月1日 上午3:17:02  
* @Description: TODO[ 系统定时任务类 ]
*/
@Service
public class QuarteFintechImpl implements QuarteFintechService {
    private static final Logger logger = LoggerFactory.getLogger(QuarteFintechImpl.class);
    @Autowired
    private RedisService redisService;
    @Autowired
    private AppConfig appConfig;
    @Autowired
    private OrderBaseinfoMapper orderBaseinfoMapper;
    @Autowired
    private LogOrderMapper logOrderMapper;
    @Autowired
    private CustBaseinfoMapper custBaseinfoMapper;
    @Autowired
    private ReturnPlanService returnPlanService;

    /* (非 Javadoc) 
    * <p>Title: wxAuthentication</p> 
    * <p>Description: </p> 
    * @throws Exception 
    * @see com.fintech.service.QuarteFintechService#wxAuthentication() 
    * 服务器启动时执行一次，之后每隔一个小时59分执行一次。
    */
    @Scheduled(fixedRate = 1000 * 60 * 59 * 2) 
    @Override
    public void wxAuthentication() throws Exception {
        try {
            String WEIXIN_API_ACCESS_TOKEN = HttpClient.get(appConfig.getWEIXIN_API_ACCESS_TOKEN_URL().replace("{appid}", appConfig.getWEIXIN_API_APPID()).replace("{secret}", appConfig.getWEIXIN_API_SECRET()));
            if(StringUtil.isEmpty(WEIXIN_API_ACCESS_TOKEN)) {
                logger.error("EK定时任务：获取微信token失败[{}]，操作时间[{}]",WEIXIN_API_ACCESS_TOKEN,DateUtils.getDateTime());
                return;
            }
            JSONObject token= JSONObject.fromObject(WEIXIN_API_ACCESS_TOKEN);
            String WEIXIN_API_JSAPI = HttpClient.get(appConfig.getWEIXIN_API_ACCESS_JSAPI_URL().replace("{accessToken}", token.get("access_token").toString()));
            JSONObject ticket= JSONObject.fromObject(WEIXIN_API_JSAPI);
            logger.info("EK定时任务：获取微信授权TOKEN[{}]>>>TICKET[{}]操作时间[{}]", token.get("access_token"), ticket.get("ticket"),
                    DateUtils.getDateTime());
            redisService.set("WEIXIN_ACCESS_TOKEN", token.get("access_token").toString());
            redisService.set("WEIXIN_TICKET", ticket.get("ticket").toString());
        } catch (Exception e) {
            logger.error("EK定时任务：获取微信token异常[{}]，操作时间[{}]",e.getMessage(),DateUtils.getDateTime());
        }
    }
    /* (非 Javadoc) 
    * <p>Title: cancelOrder</p> 
    * <p>Description: </p> 
    * @throws Exception 
    * @see com.fintech.service.QuarteFintechService#cancelOrder() 
    * 每天凌晨12:01执行 把前一天所有订单取消
    * 0 01 00 * * ?
    */
    @Scheduled(cron = "0 01 00 * * ?")
    @Override
    public void cancelOrder() throws Exception {
        logger.info("EK定时任务：自定清除当天未完成的订单》操作时间[{}]", DateUtils.getDateTime());
        List<OrderBaseinfo> orderBaseinfo=orderBaseinfoMapper.selectQuarteCancelOrder();
        for (OrderBaseinfo baseinfo : orderBaseinfo) {
            baseinfo.setOrderStatus(ConstantInterface.Enum.OrderStatus.ORDER_STATUS11.getKey());
           orderBaseinfoMapper.updateByPrimaryKeySelective(baseinfo);
            logOrderMapper.insertSelective(new LogOrder(baseinfo.getOrderId(), ConstantInterface.Enum.OrderLogStatus.ORDER_STATUS11.getKey(), ConstantInterface.Enum.OrderStatus.ORDER_STATUS11.getKey(), "系统取消：当天未完成的订单"));
        }
    }
    
    /* (非 Javadoc) 
    * <p>Title: quarteOverdueList</p> 
    * <p>Description: </p>  
    * @see com.fintech.service.QuarteFintechService#quarteOverdueList() 
    * 判断身份认证信息 是否失效
    * 0 05 00 * * ?
    */
    @Scheduled(cron = "0 05 00 * * ?")
    @Override
    public void quarteOverdueList() {
        logger.info("EK定时任务：判断客户身份证是否过期》操作时间[{}]", DateUtils.getDateTime());
        List<CustBaseinfo> custBaseinfos=custBaseinfoMapper.quarteOverdueList();
        for (CustBaseinfo custBaseinfo : custBaseinfos) {
            custBaseinfo.setIdentityStatus(String.valueOf(ConstantInterface.Enum.ConstantNumber.TOW.getKey()));
            custBaseinfoMapper.updateByPrimaryKeySelective(custBaseinfo);
        }
    }
    
    /* (非 Javadoc) 
    * <p>Title: quarteOverDueInfo</p> 
    * <p>Description: </p> 
    * @throws FintechException 
    * @see com.fintech.service.QuarteFintechService#quarteOverDueInfo() 
    * 更新逾期数据
    * 0 10 00 * * ?
    */
    @Scheduled(cron = "0 10 00 * * ?")
    @Override
    public void quarteOverDueInfo() throws FintechException {
        logger.info("EK定时任务：更新逾期数据》操作时间[{}]", DateUtils.getDateTime());
        returnPlanService.updateOverDueInfo();
    }

}
