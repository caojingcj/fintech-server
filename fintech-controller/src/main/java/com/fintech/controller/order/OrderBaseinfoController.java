package com.fintech.controller.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fintech.service.LogOrderService;

@Controller
@RequestMapping(value = "/orderbaseinfo")
public class OrderBaseinfoController {
    @Autowired
    private LogOrderService logOrderService;
    
}