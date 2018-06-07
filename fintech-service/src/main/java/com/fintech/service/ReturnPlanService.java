package com.fintech.service;

import com.fintech.xcpt.FintechException;

public interface ReturnPlanService {

    /**
     * 生成还款计划
     * @param orderId 订单号
     * @throws FintechException
     */
    public void generateReturnPlan(String orderId) throws FintechException;

    /**
     * 根据某一期来进行还款
     * @param id 编号
     * @param returnChannel 还款渠道
     * @throws FintechException
     */
    public void updateReturn(String id, String returnChannel) throws FintechException;

    /**
     * 取消订单
     * @param orderId 订单号
     * @throws FintechException
     */
    public void updateCancel(String orderId) throws FintechException;

    /**
     * 更新逾期信息
     * @param orderId 订单号
     * @throws FintechException
     */
    public void updateOverDueInfo(String orderId) throws FintechException;

    /**
     * 一次性结清
     * @param orderId 订单号
     * @throws FintechException
     */
    public void updateFullPayment(String orderId) throws FintechException;

    /**
     * 获取一次性结清金额
     * @param orderId 订单号
     * @return
     */
    public String findFullPaymentAmount(String orderId);

}
