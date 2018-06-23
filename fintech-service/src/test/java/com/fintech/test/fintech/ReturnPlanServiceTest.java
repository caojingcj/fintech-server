package com.fintech.test.fintech;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fintech.ServiceApplication;
import com.fintech.service.ReturnPlanService;
import com.fintech.xcpt.FintechException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ServiceApplication.class)
public class ReturnPlanServiceTest {

    @Autowired
    private ReturnPlanService returnPlanService;
    
    @Test
    public void testGenerateReturnPlan() {
        String orderId = "ODR000000019";
        try {
            returnPlanService.generateReturnPlan(orderId);
        } catch (FintechException e) {
            e.printStackTrace();
        }
    }
    
}
