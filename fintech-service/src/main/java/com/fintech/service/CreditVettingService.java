package com.fintech.service;

import com.fintech.enm.CreditVettingResultEnum;

public interface CreditVettingService {
    /**
     * 信审
     * @param orderId 订单号
     * @return
     */
    public CreditVettingResultEnum creditVetting(String orderId);
    
    
    void logOrder(String orderId, String result, String note);
}
