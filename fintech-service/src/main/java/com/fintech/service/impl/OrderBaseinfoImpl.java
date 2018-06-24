package com.fintech.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.fintech.dao.CompanyBaseinfoMapper;
import com.fintech.dao.LogOrderMapper;
import com.fintech.dao.OrderBaseinfoMapper;
import com.fintech.dao.OrderDetailinfoMapper;
import com.fintech.model.CompanyBaseinfo;
import com.fintech.model.LogOrder;
import com.fintech.model.OrderBaseinfo;
import com.fintech.model.OrderDetailinfo;
import com.fintech.service.OrderBaseinfoService;
import com.fintech.util.FinTechException;
import com.fintech.util.enumerator.ConstantInterface;
import com.fintech.xcpt.FintechException;
public class OrderBaseinfoImpl implements OrderBaseinfoService {

    @Autowired
    private OrderBaseinfoMapper orderBaseinfoMapper;
    @Autowired
    private OrderDetailinfoMapper orderDetailinfoMapper;
    @Autowired
    private CompanyBaseinfoMapper companyBaseinfoMapper;
    @Autowired
    private LogOrderMapper logOrderMapper;

    /* (非 Javadoc) 
    * <p>Title: insertSelective</p> 
    * <p>Description: </p> 
    * @param record
    * @throws FintechException 
    * @see com.fintech.service.OrderBaseinfoService#insertSelective(com.fintech.model.OrderBaseinfo) 
    */
    @Override
    public void insertSelective(OrderBaseinfo record) throws FintechException {
        //总在还款额不超过50w，分期还款中的笔数不超过3笔；
        Map<String, Object> amount=orderBaseinfoMapper.selectByOrderAmountJudge(record.getCustCellphone());
        if(Integer.parseInt(amount.get("amount").toString())>1||Integer.parseInt(amount.get("statusCount").toString())>3) {
            throw new FinTechException(ConstantInterface.ValidateConfig.OrderValidate.ORDER_200001.getKey(),ConstantInterface.ValidateConfig.OrderValidate.ORDER_200001.getValue());
        }
        orderBaseinfoMapper.insertSelective(record);
//        OrderDetailinfo orderDetailinfo=new OrderDetailinfo();
//        orderDetailinfoMapper.insertSelective(orderDetailinfo);
    }

    /* (非 Javadoc) 
    * <p>Title: updateByPrimaryKeySelective</p> 
    * <p>Description: </p> 
    * @param record 
    * @see com.fintech.service.OrderBaseinfoService#updateByPrimaryKeySelective(com.fintech.model.OrderBaseinfo) 
    * 项目信息填写
    */
    @Override
    public void updateByPrimaryKeySelective(OrderBaseinfo record) {
        CompanyBaseinfo baseinfo=companyBaseinfoMapper.selectByPrimaryKeyInfo(record.getCompanyId());
        if(baseinfo.getCompanyStatus().equals(String.valueOf(ConstantInterface.Enum.ConstantNumber.ZERO.getKey()))) {
            throw new FinTechException(ConstantInterface.ValidateConfig.CompanyValidate.COMPANY_100006.getKey(),ConstantInterface.ValidateConfig.CompanyValidate.COMPANY_100006.getValue());
        }
        orderBaseinfoMapper.updateByPrimaryKeySelective(record);
        logOrderMapper.insertSelective(new LogOrder(record.getOrderId(),ConstantInterface.Enum.ConstantNumber.ONE.getKey().toString(),"00", null));
    }
    public static void main(String[] args) {
        String ss="1";
        System.out.println(ss.equals(String.valueOf(ConstantInterface.Enum.ConstantNumber.ZERO.getKey())));
    }

}
