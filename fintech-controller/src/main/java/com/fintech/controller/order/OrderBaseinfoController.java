package com.fintech.controller.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fintech.enm.CreditVettingResultEnum;
import com.fintech.model.vo.OrderBaseinfoVo;
import com.fintech.model.vo.UserBaseinfoVo;
import com.fintech.model.vo.faceid.FaceidIDCardPositiveVo;
import com.fintech.service.CreditVettingService;
import com.fintech.service.LogOrderService;
import com.fintech.service.OrderBaseinfoService;
import com.fintech.service.RedisService;
import com.fintech.util.DateUtils;
import com.fintech.util.result.ResultUtils;
import com.google.gson.reflect.TypeToken;


@Controller
@RequestMapping(value = "manage/order")
public class OrderBaseinfoController {
    @Autowired
    private LogOrderService logOrderService;
    @Autowired
    private OrderBaseinfoService orderBaseinfoService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private CreditVettingService creditVettingService;
    private static final Logger logger = LoggerFactory.getLogger(OrderBaseinfoController.class);

    /** 
    * @Title: OrderBaseinfoController.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年7月13日 上午2:09:20  
    * @param @param vo
    * @param @return    设定文件 
    * @Description: TODO[ 订单列表 ]
    * @throws 
    */
    @RequestMapping(value = "queryOrderByKeyPage",method = RequestMethod.GET)
    public @ResponseBody Object queryOrderByKeyPage(OrderBaseinfoVo vo){
        logger.info("EK运营系统日志： 方法名[{}]参数[{}]操作时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),vo,DateUtils.getDateTime());
        try {
        	redisService.tokenValidate(vo.getToken());
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG,orderBaseinfoService.selectByPrimaryKeyList(vo));
        } catch (Exception e) {
            logger.error("ERROR EK运营系统日志： 方法名[{}]报错[{}] 参数[{}] 报错时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),e.getMessage(),vo,DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
        }
    }

    /** 
    * @Title: OrderBaseinfoController.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年7月13日 上午2:09:19  
    * @param @param vo
    * @param @return    设定文件 
    * @Description: TODO[ 订单详情 ]
    * @throws 
    */
    @RequestMapping(value = "selectOrderDetails",method = RequestMethod.GET)
    public @ResponseBody Object selectOrderDetails(OrderBaseinfoVo vo){
        logger.info("EK运营系统日志： 方法名[{}]参数[{}]操作时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),vo,DateUtils.getDateTime());
        try {
            redisService.tokenValidate(vo.getToken());
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG,orderBaseinfoService.selectOrderDetails(vo.getOrderId()));
        } catch (Exception e) {
            logger.error("ERROR EK运营系统日志： 方法名[{}]报错[{}] 参数[{}] 报错时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),e.getMessage(),vo,DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
        }
    }
    
    /** 
    * @Title: OrderBaseinfoController.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年7月8日 下午7:22:10  
    * @param @param vo
    * @param @return    设定文件 
    * @Description: TODO[ 取消订单 ]
    * @throws 
    */
    @RequestMapping(value = "cancelOrder",method = RequestMethod.GET)
    public @ResponseBody Object cancelOrder(OrderBaseinfoVo vo){
        try {
            redisService.tokenValidate(vo.getToken());
            logger.info("EK运营系统日志： 方法名[{}]参数[{}]操作时间[{}]操作人[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),vo,DateUtils.getDateTime(),redisService.get(vo.getToken()));
            orderBaseinfoService.cancelOrder(vo);
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG);
        } catch (Exception e) {
            logger.error("ERROR EK运营系统日志： 方法名[{}]报错[{}] 参数[{}] 报错时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),e.getMessage(),vo,DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
        }
    }
    
    /** 
    * @Title: OrderBaseinfoController.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年7月12日 上午1:19:19  
    * @param @param vo
    * @param @return    设定文件 
    * @Description: TODO[ 运营系统手动签约 ]
    * @throws 
    */
    @RequestMapping(value = "signOrder",method = RequestMethod.GET)
    public @ResponseBody Object signOrder(OrderBaseinfoVo vo){
        try {
            redisService.tokenValidate(vo.getToken());
            logger.info("EK运营系统日志： 方法名[{}]参数[{}]操作时间[{}]操作人[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),vo,DateUtils.getDateTime(),redisService.get(vo.getToken()));
            vo.setOrderNote("通过 - 运营系统>手动签署");
            orderBaseinfoService.remoteSignCaOrder(vo);
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG);
        } catch (Exception e) {
            logger.error("ERROR EK运营系统日志： 方法名[{}]报错[{}] 参数[{}] 报错时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),e.getMessage(),vo,DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
        }
    }
    
    /** 
    * @Title: OrderBaseinfoController.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年7月13日 上午1:56:17  
    * @param @param vo
    * @param @return    设定文件 
    * @Description: TODO[ 运营系统手动通过 ]
    * @throws 
    */
    @RequestMapping(value = "manualAdoptOrder",method = RequestMethod.GET)
    public @ResponseBody Object manualAdoptOrder(OrderBaseinfoVo vo){
        try {
            redisService.tokenValidate(vo.getToken());
            logger.info("EK运营系统日志： 方法名[{}]参数[{}]操作时间[{}]操作人[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),vo,DateUtils.getDateTime(),redisService.get(vo.getToken()));
        	creditVettingService.logOrder(vo.getOrderId(), CreditVettingResultEnum.通过.getValue(), "通过 - 运营系统>手动通过");
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG);
        } catch (Exception e) {
            logger.error("ERROR EK运营系统日志： 方法名[{}]报错[{}] 参数[{}] 报错时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),e.getMessage(),vo,DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
        }
    }
    
    /** 
    * @Title: OrderBaseinfoController.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年7月13日 上午2:34:41  
    * @param @param vo
    * @param @return    设定文件 
    * @Description: TODO[ 当天订单统计 ]
    * @throws 
    */
    @RequestMapping(value = "selectOrderBaseInfoTotalByToday",method = RequestMethod.GET)
    public @ResponseBody Object selectOrderBaseInfoTotalByToday(OrderBaseinfoVo vo){
    	try {
    		redisService.tokenValidate(vo.getToken());
    		logger.info("EK运营系统日志： 方法名[{}]参数[{}]操作时间[{}]操作人[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),vo,DateUtils.getDateTime(),redisService.get(vo.getToken()));
    		return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG,orderBaseinfoService.selectOrderBaseInfoTotalByToday(vo));
    	} catch (Exception e) {
    		logger.error("ERROR EK运营系统日志： 方法名[{}]报错[{}] 参数[{}] 报错时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),e.getMessage(),vo,DateUtils.getDateTime());
    		return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
    	}
    }
}