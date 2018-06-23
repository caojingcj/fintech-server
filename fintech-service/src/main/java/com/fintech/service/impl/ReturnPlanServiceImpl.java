package com.fintech.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.dao.CompanyPeriodFeeMapper;
import com.fintech.dao.LogOrderMapper;
import com.fintech.dao.OrderBaseinfoMapper;
import com.fintech.dao.UserContractMapper;
import com.fintech.dao.UserReturnplanMapper;
import com.fintech.enm.OrderOperationEnum;
import com.fintech.enm.OrderStatusEnum;
import com.fintech.enm.ReturnStatusEnum;
import com.fintech.model.CompanyPeriodFee;
import com.fintech.model.LogOrder;
import com.fintech.model.OrderBaseinfo;
import com.fintech.model.UserContract;
import com.fintech.model.UserReturnplan;
import com.fintech.service.ReturnPlanService;
import com.fintech.util.DateUtils;
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

    @Autowired
    private LogOrderMapper logOrderMapper;

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
        BigDecimal avgAmountP = TotalAmountP.divide(new BigDecimal(totalPeriod), 2, BigDecimal.ROUND_UP); // 每期还款金额（本金）
        BigDecimal lastAmountP = TotalAmountP.subtract(avgAmountP.multiply(new BigDecimal(totalPeriod - 1))); // 末期补偿金额（本金）
        BigDecimal TotalAmountI = orderAmount.multiply(rateTotal).divide(new BigDecimal(100))
                .multiply(new BigDecimal(totalPeriod)).divide(new BigDecimal(12)); // 客户还款总额（利息）
        BigDecimal avgAmountI = TotalAmountI.divide(new BigDecimal(totalPeriod), 2, BigDecimal.ROUND_UP); // 每期还款金额（利息）
        BigDecimal lastAmountI = TotalAmountI.subtract(avgAmountI.multiply(new BigDecimal(totalPeriod - 1))); // 末期补偿金额（利息）
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
    public void updateReturn(Integer id, String returnChannel) throws FintechException {
        // 获取本期还款计划
        UserReturnplan plan = userReturnplanMapper.selectByPrimaryKey(id);
        if (plan == null) {
            throw new FintechException("还款：未获取到该还款期次信息！");
        }
        // 不能跨期还款
        if (plan.getCurrentPeriod() > 1) { // 不是第一期的情况下
            UserReturnplan lastPlan = userReturnplanMapper.selectByOrderPeriod(plan.getOrderId(), plan.getCurrentPeriod() - 1);
            if (lastPlan.getReturnStatus().equals(ReturnStatusEnum.未还款.getValue())) {
                throw new FintechException("还款：暂不支持跨期还款！");
            }
        }
        // 更新还款计划
        UserReturnplan updatePlan = new UserReturnplan();
        updatePlan.setReturnStatus(ReturnStatusEnum.已还款.getValue());
        updatePlan.setReturnChannel(returnChannel);
        updatePlan.setReturnDateAc(new Date());
        updatePlan.setId(id);
        userReturnplanMapper.updateByPrimaryKeySelective(updatePlan);
        // 如果是最后一期，且所有期次都已还款，则更新订单状态
        if (plan.getCurrentPeriod() == plan.getTotalPeriod()) {
            OrderBaseinfo order = new OrderBaseinfo();
            order.setOrderStatus(OrderStatusEnum.已结清.getValue());
            order.setOrderId(plan.getOrderId());
            orderBaseinfoMapper.updateByPrimaryKeySelective(order);
        }
    }

    @Override
    public void updateCancel(String orderId) throws FintechException {
        // 更新所有的还款计划为8-已取消
        userReturnplanMapper.updateCancelByOrderId(orderId);
        // 更新订单状态为11-已取消
        OrderBaseinfo order = new OrderBaseinfo();
        order.setOrderId(orderId);
        order.setOrderStatus(OrderStatusEnum.已取消.getValue());
        orderBaseinfoMapper.updateByPrimaryKeySelective(order);
        // 新增订单操作日志
        LogOrder log = new LogOrder();
        log.setOrderId(orderId);
        log.setOrderOperation(OrderOperationEnum.取消.getValue());
        log.setOrderStatus(OrderStatusEnum.已取消.getValue());
        log.setCreateTime(new Date());
        logOrderMapper.insert(log);
    }

    @Override
    public void updateOverDueInfo() throws FintechException {
        // 获取所有应还款，但还未还款的还款计划（还款日期小于本日，且未还款的记录）
        List<UserReturnplan> planList = userReturnplanMapper.selectOverDueList();
        // 循环还款计划，并更新逾期信息
        for (UserReturnplan plan : planList) {
            UserReturnplan overDuePlan = new UserReturnplan();
            overDuePlan.setIsOverdue(true); // 是否逾期
            long days = DateUtils.getBetweenDays(DateUtils.format(plan.getReturnDate(), "yyyy-MM-dd"), DateUtils.format(new Date(), "yyyy-MM-dd"));
            overDuePlan.setOverdueDays(Integer.valueOf(String.valueOf(days))); // 逾期天数
            BigDecimal overdueAmount = new BigDecimal(0);
            // T≦7天：当期本金*0.5%*天数
            if (days <= 7) {
                overdueAmount = plan.getPrincipalAmount().multiply(new BigDecimal(0.005)).multiply(new BigDecimal(days)).setScale(2, BigDecimal.ROUND_UP);
            }
            // 7天<T≦60天：当期本金*1%*天数
            if (days <= 60 && days > 7) {
                overdueAmount = plan.getPrincipalAmount().multiply(new BigDecimal(0.01)).multiply(new BigDecimal(days)).setScale(2, BigDecimal.ROUND_UP);
            }
            // T>60天：当期本金*1%*60
            if (days > 60) {
                overdueAmount = plan.getPrincipalAmount().multiply(new BigDecimal(0.01)).multiply(new BigDecimal(60)).setScale(2, BigDecimal.ROUND_UP);
            }
            overDuePlan.setOverdueAmount(overdueAmount); // 逾期金额
            overDuePlan.setId(plan.getId()); // id
            userReturnplanMapper.updateByPrimaryKeySelective(overDuePlan);
        }
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
    public BigDecimal findReturnAmount(double orderAmount, double rateTotal, int totalPeriod) {
        BigDecimal TotalAmountP = new BigDecimal(orderAmount); // 客户还款总额（本金）
        // BigDecimal avgAmountP = TotalAmountP.divide(new
        // BigDecimal(totalPeriod)).setScale(2, BigDecimal.ROUND_UP); // 每期还款金额（本金）
        BigDecimal TotalAmountI = TotalAmountP.multiply(new BigDecimal(rateTotal)).divide(new BigDecimal(100))
                .multiply(new BigDecimal(totalPeriod)).divide(new BigDecimal(12)); // 客户还款总额（利息）
        // BigDecimal avgAmountI = TotalAmountI.divide(new
        // BigDecimal(totalPeriod)).setScale(2, BigDecimal.ROUND_UP); // 每期还款金额（利息）
        // BigDecimal lastAmountI = TotalAmountI.subtract(avgAmountI.multiply(new
        // BigDecimal(totalPeriod - 1))); // 末期补偿金额（利息）
        return TotalAmountP.add(TotalAmountI);
    }

}
