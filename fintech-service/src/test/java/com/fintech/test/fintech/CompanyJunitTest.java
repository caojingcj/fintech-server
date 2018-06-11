package com.fintech.test.fintech;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fintech.ServiceApplication;
import com.fintech.dao.CompanyBaseinfoMapper;
import com.fintech.model.CompanyBaseinfo;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ServiceApplication.class)
public class CompanyJunitTest {
    @Autowired
    private CompanyBaseinfoMapper baseinfoMapper;

    @Test
    public void selectBaseInfo() {
        CompanyBaseinfo baseinfo = baseinfoMapper.selectByPrimaryKeyInfo("000002");
        System.out.println(baseinfo);
    }
}
