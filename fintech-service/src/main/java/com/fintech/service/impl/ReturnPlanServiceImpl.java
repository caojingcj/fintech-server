package com.fintech.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.dao.CompanyPeriodFeeMapper;
import com.fintech.dao.OrderBaseinfoMapper;
import com.fintech.dao.UserContractMapper;
import com.fintech.dao.UserReturnplanMapper;
import com.fintech.model.CompanyPeriodFee;
import com.fintech.model.OrderBaseinfo;
import com.fintech.model.UserContract;
import com.fintech.model.UserReturnplan;
import com.fintech.service.ReturnPlanService;
import com.fintech.xcpt.FintechException;

@Service
public class ReturnPlanServiceImpl implements ReturnPlanService {

    @Autowired
    private OrderBaseinfoMapper orderBaseinfoMapper;

    @Autowired
    private UserReturnplanMapper userReturnplanMapper;

    @Autowired
    private UserContractMapper userContractMapper;

    @Autowired
    private CompanyPeriodFeeMapper companyPeriodFeeMapper;

    @Override
    public void generateReturnPlan(String orderId) throws FintechException {
        // 获取订单信息
        OrderBaseinfo order = orderBaseinfoMapper.selectByPrimaryKey(orderId);
        if (order == null) {
            throw new FintechException("生成还款计划：没有获取到订单信息！");
        }
        BigDecimal orderAmount = order.getOrderAmount(); // 订单金额
        // 获取合同信息
        UserContract contract = userContractMapper.selectByOrderId(orderId);
        if (contract == null) {
            throw new FintechException("生成还款计划：没有获取到合同信息！");
        }
        String contractId = contract.getContractId(); // 合同号
        Date contractDate = contract.getCreateTime(); // 合同时间
        // 获取商户费率配置
        String companyId = order.getCompanyId(); // 商户编号
        Integer totalPeriod = order.getTotalPeriod(); // 期数
        CompanyPeriodFee cpf = companyPeriodFeeMapper.selectByCompanyIdAndPeriod(companyId, totalPeriod);
        if (cpf == null) {
            throw new FintechException("生成还款计划：没有获取到商户费率配置信息！");
        }
        BigDecimal rateTotal = cpf.getRateTotal(); // 总费率
        BigDecimal rateManage = cpf.getRateManage(); // 管理费率
        // 计算金额
        BigDecimal manageAmount = orderAmount.multiply(rateManage).divide(new BigDecimal(100)); // 管理费总额
        BigDecimal TotalAmountP = orderAmount; // 客户还款总额（本金）
        BigDecimal avgAmountP = TotalAmountP.divide(new BigDecimal(totalPeriod)).setScale(2, BigDecimal.ROUND_UP); // 每期还款金额（本金）
        BigDecimal lastAmountP = TotalAmountP.subtract(avgAmountP.multiply(new BigDecimal(totalPeriod - 1)));// 末期补偿金额（本金）
        BigDecimal TotalAmountI = orderAmount.multiply(rateTotal).divide(new BigDecimal(100)); // 客户还款总额（利息）
        BigDecimal avgAmountI = TotalAmountI.divide(new BigDecimal(totalPeriod)).setScale(2, BigDecimal.ROUND_UP); // 每期还款金额（利息）
        BigDecimal lastAmountI = TotalAmountI.subtract(avgAmountI.multiply(new BigDecimal(totalPeriod - 1)));// 末期补偿金额（利息）
        // 保存还款计划至数据库
        for (int i = 1; i <= totalPeriod; i++) {
            UserReturnplan plan = new UserReturnplan();
            plan.setOrderId(orderId);
            plan.setContractId(contractId);
            plan.setCustCellphone(order.getCustCellphone());
            plan.setCompanyId(companyId);
            plan.setCurrentPeriod(i);
            plan.setTotalPeriod(totalPeriod);
            Calendar cd = Calendar.getInstance();
            cd.setTime(contractDate);
            cd.add(Calendar.MONTH, i);
            plan.setReturnDate(cd.getTime());
            if (i != totalPeriod) {
                plan.setPrincipalAmount(avgAmountP);
                plan.setInterestAmount(avgAmountI);
            } else { // 末期补偿
                plan.setPrincipalAmount(lastAmountP);
                plan.setInterestAmount(lastAmountI);
            }
            if (i == 1) { // 第一期收取所有管理费
                plan.setManagementAmount(manageAmount);
            }
            userReturnplanMapper.insertSelective(plan);
        }
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

    @Override
    public String findReturnAmount(String orderId) {
        // TODO Auto-generated method stub
        return null;
    }

}
