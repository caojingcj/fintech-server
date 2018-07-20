package com.fintech.test.fintech;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fintech.ServiceApplication;
import com.fintech.enm.CreditVettingResultEnum;
import com.fintech.model.vo.OrderBaseinfoVo;
import com.fintech.service.CreditVettingService;
import com.fintech.service.OrderBaseinfoService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ServiceApplication.class)
public class OrderBaseInfoTest {
    
    @Autowired
    private OrderBaseinfoService orderBaseinfoService;
    
    @Autowired
    private CreditVettingService creditVettingService;
    
    String orderId = "ODR000000419";
    
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
    	 vo.setOrderNote("通过 - 运营系统>人工签署");
        System.out.println(orderBaseinfoService.remoteSignCaOrder(vo));
    }
    
    /**
     * @throws Exception  
    * @Title: OrderBaseInfoTest.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年7月8日 下午6:20:19  
    * @param     设定文件 
    * @Description: TODO[ 人工通过 ]
    * @throws 
    */
    @Test
    public void testAdoptOrder() throws Exception {
    	OrderBaseinfoVo vo=new OrderBaseinfoVo();
    	vo.setOrderId(orderId);
    	vo.setOrderNote("通过 - 运营系统>人工通过");
    	creditVettingService.logOrder(orderId, CreditVettingResultEnum.通过.getValue(), "");
//    	System.out.println(creditVettingService.creditVetting(orderId));
    }
    
    /** 
    * @Title: OrderBaseInfoTest.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年7月19日 下午2:55:51  
    * @param @throws Exception    设定文件 
    * @Description: TODO[ 客户取消 ]
    * @throws 
    */
    @Test
    public void testCancelOrder() throws Exception {
    	OrderBaseinfoVo vo=new OrderBaseinfoVo();
    	vo.setOrderId(orderId);
    	orderBaseinfoService.cancelOrder(vo);
    }

}
