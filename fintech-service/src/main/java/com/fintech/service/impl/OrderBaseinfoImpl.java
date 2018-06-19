package com.fintech.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.fintech.dao.OrderBaseinfoMapper;
import com.fintech.dao.OrderDetailinfoMapper;
import com.fintech.model.OrderBaseinfo;
import com.fintech.model.OrderDetailinfo;
import com.fintech.service.OrderBaseinfoService;

public class OrderBaseinfoImpl implements OrderBaseinfoService {

    @Autowired
    private OrderBaseinfoMapper orderBaseinfoMapper;
    @Autowired
    private OrderDetailinfoMapper orderDetailinfoMapper;

    @Override
    public void insertSelective(OrderBaseinfo record) {
        //总在还款额不超过50w，分期还款中的笔数不超过3笔；
        Map<String, Object>parms=new HashMap<>();
        parms.put("custCellphone", record.getCustCellphone());
        orderBaseinfoMapper.selectByPrimaryKey(parms);
        orderBaseinfoMapper.insertSelective(record);
        OrderDetailinfo orderDetailinfo=new OrderDetailinfo();
        orderDetailinfoMapper.insertSelective(orderDetailinfo);
    }

    @Override
    public void updateByPrimaryKeySelective(OrderBaseinfo record) {

    }

}
