package com.fintech.controller.order;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fintech.common.properties.AppConfig;
import com.fintech.model.vo.OrderAttachmentVo;
import com.fintech.model.vo.OrderBaseinfoVo;
import com.fintech.model.vo.OrderDetailinfoVo;
import com.fintech.model.vo.ProjectVo;
import com.fintech.service.CompanyBaseinfoService;
import com.fintech.service.CompanyChannelService;
import com.fintech.service.CompanyItemService;
import com.fintech.service.CompanyPeriodFeeService;
import com.fintech.service.LogSmsMessageService;
import com.fintech.service.OrderBaseinfoService;
import com.fintech.service.RedisService;
import com.fintech.util.DateUtils;
import com.fintech.util.result.BaseResult;
import com.fintech.util.result.ResultUtils;

/**   
* @Title: OrderBaseInfoController.java 
* @Package com.fintech.controller.order 
* @author qierkang xyqierkang@163.com   
* @date 2018年6月25日 下午6:26:37  
* @Description: TODO[ 订单控制器 ]
*/
@Controller
@RequestMapping("app/orderbaseinfo")
public class OrderBaseInfoController {
    private static final Logger logger = LoggerFactory.getLogger(OrderBaseInfoController.class);
    @Autowired
    private AppConfig appConfig;
    @Autowired
    private RedisService redisService;
    @Autowired
    private CompanyBaseinfoService companyBaseinfoService;
    @Autowired
    private LogSmsMessageService logSmsMessageService;
    @Autowired
    private CompanyChannelService companyChannelService;
    @Autowired
    private CompanyPeriodFeeService companyPeriodFeeService;
    @Autowired
    private CompanyItemService companyItemService;
    @Autowired
    private OrderBaseinfoService orderBaseinfoService;

    /** 
    * @Title: OrderBaseInfoController.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年6月25日 下午6:26:29  
    * @param @param mobile
    * @param @return    设定文件 
    * @Description: TODO[ 进件扫码 ]
    * @throws 
    */
    @RequestMapping(value = "scanPiece",method = RequestMethod.GET)
    public @ResponseBody BaseResult appLogin(String mobile,String token) {
        try {
            redisService.tokenValidate(token);
            logger.info("EK 用户扫码进件[[{}][{}]]方法名[{}]操作时间[{}]",mobile,token,Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
            // Map<String, Object>params=CommonUtil.object2Map(vo);
            // Map<String, Object>params=new HashMap<String, Object>();
            // params.put("companyId", "999999");
            // 拿商户999999测试 generateVerifyCode
            String companyId = "999999";
            // 用户已经有过记录 直接跑出商户信息
          Map<String, Object>resultMap=orderBaseinfoService.scanPiece(companyId,mobile);
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG, resultMap);
        } catch (Exception e) {
            logger.error("ERROR EK参数[{}] 报错[{}] 方法名[{}]报错时间[{}]", mobile,e.getMessage(),
                    Thread.currentThread().getStackTrace()[1].getMethodName(), DateUtils.getDateTime());
            return ResultUtils.error(e.getMessage());
        }
    }
    
    /** 
    * @Title: OrderBaseInfoController.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年6月25日 下午8:16:07  
    * @param @param projectVo
    * @param @return    设定文件 
    * @Description: TODO[ 进件项目填写 ]
    * @throws 
    */
    @RequestMapping(value = "saveProject",method = RequestMethod.POST)
    public @ResponseBody Object saveProject(ProjectVo projectVo) {
        try {
            redisService.tokenValidate(projectVo.getToken());
            logger.info("EK 用户进件项目填写[[{}]方法名[{}]操作时间[{}]",projectVo,Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
            orderBaseinfoService.saveProject(projectVo);
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG);
        } catch (Exception e) {
            logger.error("ERROR EK参数[{}] 报错[{}] 方法名[{}]报错时间[{}]", projectVo.toString(),e.getMessage(),
                    Thread.currentThread().getStackTrace()[1].getMethodName(), DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE, e.getMessage());
        }
    }
    
    /** 
    * @Title: OrderBaseInfoController.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年6月26日 上午3:23:58  
    * @param @param orderDetailinfo
    * @param @return    设定文件 
    * @Description: TODO[ 用户个人信息填写 ]
    * @throws 
    */
    @RequestMapping(value = "saveDetailinfo",method = RequestMethod.POST)
    public @ResponseBody Object saveDetailinfo(OrderDetailinfoVo orderDetailinfo) {
        try {
            redisService.tokenValidate(orderDetailinfo.getToken());
            logger.info("EK 用户进件项目填写[[{}]方法名[{}]操作时间[{}]",orderDetailinfo,Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
            orderBaseinfoService.saveDetailinfo(orderDetailinfo);
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG);
        } catch (Exception e) {
            logger.error("ERROR EK参数[{}] 报错[{}] 方法名[{}]报错时间[{}]", orderDetailinfo.toString(),e.getMessage(),
                    Thread.currentThread().getStackTrace()[1].getMethodName(), DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE, e.getMessage());
        }
    }
    
    /** 
    * @Title: OrderBaseInfoController.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年6月26日 上午4:18:48  
    * @param @param vo
    * @param @param multipartfiles
    * @param @return    设定文件 
    * @Description: TODO[ 用户附件上传 ]
    * @throws 
    */
    @RequestMapping(value = "saveOrderAttachment",method = RequestMethod.POST)
    public @ResponseBody Object saveOrderAttachment(OrderAttachmentVo vo,MultipartHttpServletRequest multipartHttpServletRequest) {
        try {
            redisService.tokenValidate(vo.getToken());
            logger.info("EK 用户附件上传[{}]方法名[{}]操作时间[{}]",vo,Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
            String url=orderBaseinfoService.saveOrderAttachment(vo, multipartHttpServletRequest);
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG,url);
        } catch (Exception e) {
            logger.error("ERROR EK参数[{}] 报错[{}] 方法名[{}]报错时间[{}]", vo.toString(),e.getMessage(),
                    Thread.currentThread().getStackTrace()[1].getMethodName(), DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE, e.getMessage());
        }
    }
    
    @RequestMapping(value = "remoteSignCaOrder",method = RequestMethod.GET)
    public @ResponseBody Object remoteSignCaOrder(OrderBaseinfoVo vo) {
        try {
            redisService.tokenValidate(vo.getToken());
            logger.info("EK 用户附件上传[{}]方法名[{}]操作时间[{}]",vo,Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
            String url=orderBaseinfoService.remoteSignCaOrder(vo);
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG,url);
        } catch (Exception e) {
            logger.error("ERROR EK参数[{}] 报错[{}] 方法名[{}]报错时间[{}]", vo.toString(),e.getMessage(),
                    Thread.currentThread().getStackTrace()[1].getMethodName(), DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE, e.getMessage());
        }
    }
    
}
