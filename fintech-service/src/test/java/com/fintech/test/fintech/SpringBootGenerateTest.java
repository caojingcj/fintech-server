package com.fintech.test.fintech;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fintech.ServiceApplication;
import com.fintech.dao.CompanyBaseinfoMapper;
import com.fintech.dao.procedure.CompanyProcedureMapper;
import com.fintech.dao.procedure.ContractProcedureMapper;
import com.fintech.dao.procedure.OrderProcedureMapper;
import com.fintech.dao.procedure.ProcedureXmlMapp;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ServiceApplication.class)
public class SpringBootGenerateTest {
    @Autowired
    CompanyProcedureMapper companyProcedure;
    @Autowired
    ContractProcedureMapper contractProcedureMapper;
    @Autowired
    OrderProcedureMapper orderProcedureMapper;
    @Autowired
    ProcedureXmlMapp procedureXmlMapp;
    
    @Test
    public void generateCompanyId() {
        try {
            //Mapper调用方式
            System.out.println(companyProcedure.generateCompanyId());//商户编号
            System.out.println(contractProcedureMapper.generateContractId());//合同编号
            System.out.println(orderProcedureMapper.generateOrderId());//订单编号
            //XML调用方式
            System.out.println(procedureXmlMapp.generateCompanyId());
            System.out.println(procedureXmlMapp.generateContractId());
            System.out.println(procedureXmlMapp.generateOrderId());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
