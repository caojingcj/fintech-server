package com.fintech.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.fintech.xcpt.FintechException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
    
    @Autowired
    private ReturnPlanService returnPlanService;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    public void testGenerateReturnPlan() {
        String orderId = "";
        try {
            returnPlanService.generateReturnPlan(orderId);
        } catch (FintechException e) {
            e.printStackTrace();
        }
//        assertTrue(true);
    }
}
