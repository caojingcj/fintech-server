package com.fintech.test.fintech;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fintech.ServiceApplication;
import com.fintech.model.vo.OrderBaseinfoVo;
import com.fintech.service.OrderBaseinfoService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ServiceApplication.class)
public class OrderBaseInfoTest {
    
    @Autowired
    private OrderBaseinfoService orderBaseinfoService;
    String orderId = "ODR000000231";
    
    /**
     * @throws Exception  
    * @Title: OrderBaseInfoTest.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年7月8日 下午6:20:19  
    * @param     设定文件 
    * @Description: TODO[ 用户签署 ]
    * @throws 
    */
    @Test
    public void testRemoteSignCaOrder() throws Exception {
    	OrderBaseinfoVo vo=new OrderBaseinfoVo();
    	vo.setOrderId(orderId);
        System.out.println(orderBaseinfoService.remoteSignCaOrder(vo));
    }

}
