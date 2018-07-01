package com.fintech.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.fintech.dao.CustBaseinfoMapper;
import com.fintech.dao.LogOrderMapper;
import com.fintech.dao.OrderBaseinfoMapper;
import com.fintech.dao.OrderDetailinfoMapper;
import com.fintech.enm.CreditVettingResultEnum;
import com.fintech.enm.EducationStatusEnum;
import com.fintech.enm.GenderEnum;
import com.fintech.enm.IdentityStatusEnum;
import com.fintech.enm.OrderOperationEnum;
import com.fintech.enm.OrderStatusEnum;
import com.fintech.model.CustBaseinfo;
import com.fintech.model.LogOrder;
import com.fintech.model.OrderBaseinfo;
import com.fintech.model.OrderDetailinfo;
import com.fintech.service.CreditVettingService;
import com.fintech.util.IDCardUtil;
@Service
public class CreditVettingServiceImpl implements CreditVettingService {
    
    @Autowired
    private OrderBaseinfoMapper orderBaseinfoMapper;
    
    @Autowired
    private OrderDetailinfoMapper orderDetailinfoMapper;
    
    @Autowired
    private CustBaseinfoMapper custBaseinfoMapper;
    
    @Autowired
    private LogOrderMapper logOrderMapper;

    @Override
    public CreditVettingResultEnum creditVetting(String orderId) {
        // 拒绝 - 年龄18岁以下
        OrderBaseinfo orderBaseInfo = orderBaseinfoMapper.selectByPrimaryKey(orderId);
        String custId = orderBaseInfo.getCustIdCardNo();
        int age = IDCardUtil.getAgeByIdCard(custId);
        if (age < 18) {
            logOrder(orderId, CreditVettingResultEnum.拒绝.getValue(), "拒绝 - 年龄18岁以下");
            return CreditVettingResultEnum.拒绝;
        }
        // 拒绝 - 年龄60岁以上
        if (age > 60) {
            logOrder(orderId, CreditVettingResultEnum.拒绝.getValue(), "拒绝 - 年龄60岁以上");
            return CreditVettingResultEnum.拒绝;
        }
        // 拒绝 - 身份证有效期失效
        CustBaseinfo custBaseInfo = custBaseinfoMapper.selectByPrimaryKey(orderBaseInfo.getCustCellphone());
        if (custBaseInfo.getIdentityStatus().equals(IdentityStatusEnum.未认证.getValue()) || custBaseInfo.getIdentityStatus().equals(IdentityStatusEnum.已过期.getValue())) {
            logOrder(orderId, CreditVettingResultEnum.拒绝.getValue(), "拒绝 - 身份证有效期失效");
            return CreditVettingResultEnum.拒绝;
        }
        // 拒绝 - 年龄>30且性别为男性
        if (age > 30 && IDCardUtil.getGenderByIdCard(custId).equals(GenderEnum.男.getValue())) {
            logOrder(orderId, CreditVettingResultEnum.拒绝.getValue(), "拒绝 - 年龄>30且性别为男性");
            return CreditVettingResultEnum.拒绝;
        }
        // 拒绝 - 年龄<22且申请额度>5w
        if (age < 22 && orderBaseInfo.getOrderAmount().intValue() > 50000) {
            logOrder(orderId, CreditVettingResultEnum.拒绝.getValue(), "拒绝 - 年龄<22且申请额度>5w");
            return CreditVettingResultEnum.拒绝;
        }
        // 拒绝 - 学生（年龄18-21岁；学历 专科/本科/硕士及以上）
        OrderDetailinfo orderDetailinfo = orderDetailinfoMapper.selectByPrimaryKey(orderId);
        String educationStatus = orderDetailinfo.getEducationalStatus();
        if (age >= 18 && age <= 21 && (educationStatus.equals(EducationStatusEnum.专科.getValue()) || educationStatus.equals(EducationStatusEnum.本科.getValue()) || educationStatus.equals(EducationStatusEnum.硕士.getValue()) || educationStatus.equals(EducationStatusEnum.博士.getValue()))) {
            logOrder(orderId, CreditVettingResultEnum.拒绝.getValue(), "拒绝 - 学生（年龄18-21岁；学历 专科/本科/硕士及以上）");
            return CreditVettingResultEnum.拒绝;
        }
        // 拒绝 - 同一申请人申请超过3笔
        // 拒绝 - 同一申请人借款金额高于50w
        // 拒绝 - face++低于默认分 - 默认分
        // 拒绝 - 黑名单库（自有）- 是
        // 拒绝 - 魔蝎黑名单 - 是
        // 拒绝 - 手机报告、通讯录都无效(两者都无效)
        // 拒绝 - 联系人联系电话不在手机报告或通讯录中
        // 拒绝 - 支付定金,且定金比例 < 申请金额的30%
        // 拒绝 - 咨询师黑名单(根据医院名单设置)
        // 通过(且) - 近三月通话号码>=10
        // 通过(且) - 近三月互通电话>=3
        // 通过(且) - 互通定义，主叫和被叫都有记录
        // 通过(且) - 通讯录号码>=10
        return CreditVettingResultEnum.拒绝;
    }
    
    private void logOrder(String orderId, String result, String note) {
        // 新增订单操作日志
        LogOrder log = new LogOrder();
        log.setOrderId(orderId);
        log.setOrderOperation(OrderOperationEnum.审批.getValue());
        if (result.equals(CreditVettingResultEnum.通过.getValue())) {
            log.setOrderStatus(OrderStatusEnum.分期还款中.getValue());
        }
        if (result.equals(CreditVettingResultEnum.拒绝.getValue())) {
            log.setOrderNote(note);
            log.setOrderStatus(OrderStatusEnum.审批拒绝.getValue());
        }
        log.setCreateTime(new Date());
        logOrderMapper.insert(log);
    }

}
