package com.fintech.controller.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fintech.model.vo.CompanyBaseinfoVo;
import com.fintech.model.vo.OrderBaseinfoVo;
import com.fintech.service.LogOrderService;
import com.fintech.service.OrderBaseinfoService;
import com.fintech.util.DateUtils;
import com.fintech.util.result.ResultUtils;


@Controller
@RequestMapping(value = "/order")
public class OrderBaseinfoController {
    @Autowired
    private LogOrderService logOrderService;
    @Autowired
    private OrderBaseinfoService orderBaseinfoService;
    private static final Logger logger = LoggerFactory.getLogger(OrderBaseinfoController.class);

    @RequestMapping(value = "queryOrderByKeyPage",method = RequestMethod.GET)
    public @ResponseBody Object queryOrderByKeyPage(OrderBaseinfoVo vo){
        logger.info("EK 参数[{}]方法名[{}]操作时间[{}]操作人[{}]",vo,Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
        try {
            vo.setCompanyIds("000001");
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG,orderBaseinfoService.selectByPrimaryKeyList(vo));
        } catch (Exception e) {
            logger.error("ERROR EK 参数[{}] 报错[{}] 方法名[{}]报错时间[{}]",vo,e.getMessage(),Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
        }
    }

}