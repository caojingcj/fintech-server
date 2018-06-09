package com.fintech.test.fintech;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fintech.ServiceApplication;
import com.fintech.dao.CompanyBaseinfoMapper;
import com.fintech.model.CompanyBaseinfo;
import com.fintech.service.CompanyChannelService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ServiceApplication.class)
public class CompanyJunitTest {
    @Autowired
    private CompanyChannelService companyChannelService;
    @Autowired
    private CompanyBaseinfoMapper baseinfoMapper;

    @Test
    public void companyChannelInf() {
        System.out.println(companyChannelService.companyChannelInfo());
    }
    @Test
    public void selectBaseInfo() {
        CompanyBaseinfo baseinfo = baseinfoMapper.selectByPrimaryKeyInfo();
        System.out.println(baseinfo);
    }
}
