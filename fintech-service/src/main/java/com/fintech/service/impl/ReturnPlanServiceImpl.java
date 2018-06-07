package com.fintech.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.dao.OrderBaseinfoMapper;
import com.fintech.dao.UserReturnplanMapper;
import com.fintech.service.ReturnPlanService;
import com.fintech.xcpt.FintechException;

@Service
public class ReturnPlanServiceImpl implements ReturnPlanService {

    @Autowired
    private OrderBaseinfoMapper orderBaseinfoMapper;

    @Autowired
    private UserReturnplanMapper userReturnplanMapper;

    @Override
    public void generateReturnPlan(String orderId) throws FintechException {
        // 获取订单信息
        // 获取商户费率配置
        // 计算每期金额
        // 保存还款计划至数据库
    }

    @Override
    public void updateReturn(String id, String returnChannel) throws FintechException {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateCancel(String orderId) throws FintechException {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateOverDueInfo(String orderId) throws FintechException {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateFullPayment(String orderId) throws FintechException {
        // TODO Auto-generated method stub

    }

    @Override
    public String findFullPaymentAmount(String orderId) {
        // TODO Auto-generated method stub
        return null;
    }

}
