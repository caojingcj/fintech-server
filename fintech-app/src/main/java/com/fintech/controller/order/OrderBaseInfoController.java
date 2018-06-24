package com.fintech.controller.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fintech.common.properties.AppConfig;
import com.fintech.model.LogSmsMessage;

@Controller
@RequestMapping("app/orderbaseinfo")
public class OrderBaseInfoController {
    private static final Logger logger = LoggerFactory.getLogger(OrderBaseInfoController.class);
    @Autowired
    private AppConfig appConfig;
    
}
