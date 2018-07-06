package com.fintech.test.fintech;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fintech.ServiceApplication;
import com.fintech.service.CreditVettingService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ServiceApplication.class)
public class CreditVettingTest {
    
    @Autowired
    private CreditVettingService creditVettingService;
    
    @Test
    public void testCreditVetting() {
        String orderId = "ODR000000231";
        System.out.println(creditVettingService.creditVetting(orderId));
    }

}
