package com.fintech.service;

import com.fintech.enm.CreditVettingResultEnum;

public interface CreditVettingService {

    /**
     * 信审
     * @param orderId 订单号
     * @return
     */
    public CreditVettingResultEnum creditVetting(String orderId) {
        // 拒绝 - 年龄18岁以下
        // 拒绝 - 年龄60岁以上
        // 拒绝 - 身份证有效期失效
        // 拒绝 - 学生（年龄18-21岁；学历 专科/本科/硕士及以上）
        // 拒绝 - 同一申请人申请超过3笔
        // 拒绝 - 同一申请人借款金额高于50w
        // 拒绝 - face++低于默认分 - 默认分
        // 拒绝 - 黑名单库（自有）- 是
        // 拒绝 - 魔蝎黑名单 - 是
        // 拒绝 - 手机报告、通讯录都无效(两者都无效)
        // 拒绝 - 联系人联系电话不在手机报告或通讯录中
        // 拒绝 - 支付定金,且定金比例 < 申请金额的30%
        // 拒绝 - 咨询师黑名单(根据医院名单设置)
        // 拒绝 - 年龄>30且性别为男性
        // 拒绝 - 年龄<22且申请额度>5w
        // 通过(且) - 近三月通话号码>=10
        // 通过(且) - 近三月互通电话>=3
        // 通过(且) - 互通定义，主叫和被叫都有记录
        // 通过(且) - 通讯录号码>=10
    }
    
}
