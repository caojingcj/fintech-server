package com.fintech.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fintech.ServiceApplication;
import com.fintech.dao.CompanyBaseinfoMapper;
import com.fintech.service.CompanyChannelService;

/**   
* @Title: CompanyTest.java 
* @Package com.fintech.test 
* @author qierkang xyqierkang@163.com   
* @date 2018年6月10日 上午5:49:01  
* @Description: TODO[ 这个类作为案例 Junit 测试类 勿改 ]
*/
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
        System.out.println(baseinfoMapper.selectByPrimaryKeyInfo());
    }
}
