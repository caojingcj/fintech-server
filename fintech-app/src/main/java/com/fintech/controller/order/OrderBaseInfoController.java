package com.fintech.controller.order;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fintech.common.properties.AppConfig;
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
import com.fintech.util.BeanUtils;
import com.fintech.util.DateUtils;
import com.fintech.util.StringUtil;
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
//    @RequestMapping("scanPiece")
//    public @ResponseBody BaseResult scanPiece(HttpServletRequest request, HttpServletResponse response) {
//        try {
//           String token=redisService.get(request.getParameter("openId"));
//           String mobile= redisService.get(token);
//           logger.info("EK 客户扫码进件mobile[{}]token[{}]openId[{}]toUserName[{}]方法名[{}]操作时间[{}]",mobile,token,request.getParameter("openId"),request.getParameter("toUserName"),Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
//            // Map<String, Object>params=CommonUtil.object2Map(vo);
//            // Map<String, Object>params=new HashMap<String, Object>();
//            // params.put("companyId", "999999");
//            // 拿商户999999测试 generateVerifyCode
//            // 客户已经有过记录 直接跑出商户信息
//          Map<String, Object>resultMap=orderBaseinfoService.scanPiece(request.getParameter("companyId"),mobile);
//            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG, resultMap);
////            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG, null);
//        } catch (Exception e) {
//            logger.error("ERROR EK 报错[{}] 方法名[{}]报错时间[{}]",e.getMessage(),
//                    Thread.currentThread().getStackTrace()[1].getMethodName(), DateUtils.getDateTime());
//            return ResultUtils.error(e.getMessage());
//        }
//    }
    @RequestMapping(value = "scanPiece",method = RequestMethod.GET)
    public @ResponseBody BaseResult appLogin(String mobile,String token) {
        try {
            redisService.tokenValidate(token);
            logger.info("EK 客户扫码进件[mobile[{}]token[{}]]方法名[{}]操作时间[{}]",mobile,token,Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
            // Map<String, Object>params=CommonUtil.object2Map(vo);
            // Map<String, Object>params=new HashMap<String, Object>();
            // params.put("companyId", "999999");
            // 拿商户999999测试 generateVerifyCode
            String companyId = "000001";
            // 客户已经有过记录 直接跑出商户信息
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
    public @ResponseBody Object saveProject(@RequestBody ProjectVo projectVo) {
        try {
            redisService.tokenValidate(projectVo.getToken());
            logger.info("EK>APP系统日志： 方法名[{}]参数[[{}]操作时间[{}]操作人[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),projectVo,DateUtils.getDateTime(),redisService.get(projectVo.getToken()));
            orderBaseinfoService.saveProject(projectVo);
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG);
        } catch (Exception e) {
        	e.printStackTrace();
            logger.error("ERROR EK>APP系统日志：方法名[{}]参数[{}]报错[{}] 报错时间[{}]", 
                    Thread.currentThread().getStackTrace()[1].getMethodName(), projectVo,e.getMessage(),DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE, e.getMessage());
        }
    }
    
    /** 
    * @Title: OrderBaseInfoController.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年6月27日 上午5:11:28  
    * @param @param custBaseinfoVo
    * @param @return    设定文件 
    * @Description: TODO[ 客户身份认证 正面]
    * @throws 
    */
    @RequestMapping(value = "saveIdentityPositive",method = RequestMethod.GET)
    public @ResponseBody Object saveIdentityPositive(String serverId,String token,String orderId) {
        try {
            redisService.tokenValidate(token);
            logger.info("EK>APP系统日志： 方法名[{}]客户身份认证[serverId[{}]token[{}]orderId[{}]操作时间[{}]操作人[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),serverId,token,orderId,DateUtils.getDateTime(),redisService.get(token));
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG,orderBaseinfoService.saveIdentityPositive(serverId, token, orderId));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("ERROR EK>APP系统日志：方法名[{}]参数[{}] 报错[{}] 报错时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),  serverId,orderId,e.getMessage(),
                    DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE, e.getMessage());
        }
    }
    
    /** 
    * @Title: OrderBaseInfoController.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年7月1日 下午2:55:16  
    * @param @param serverId
    * @param @param token
    * @param @param orderId
    * @param @return    设定文件 
    * @Description: TODO[ 客户身份认证反面 ]
    * @throws 
    */
    @RequestMapping(value = "saveIdentitySide",method = RequestMethod.GET)
    public @ResponseBody Object saveIdentitySide(String serverId,String token,String orderId) {
        try {
            redisService.tokenValidate(token);
            logger.info("EK>APP系统日志： 方法名[{}]客户身份认证[serverId[{}]token[{}]orderId[{}]操作时间[{}]操作人[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),serverId,token,orderId,DateUtils.getDateTime(),redisService.get(token));
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG,orderBaseinfoService.saveIdentitySide(serverId, token, orderId));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("ERROR EK>APP系统日志：参数[serverId{}orderId{}] 报错[{}] 方法名[{}]报错时间[{}]", serverId,orderId,e.getMessage(),
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
    * @Description: TODO[ 客户个人信息填写 ]
    * @throws 
    */
    @RequestMapping(value = "saveDetailinfo",method = RequestMethod.POST)
    public @ResponseBody Object saveDetailinfo(@RequestBody OrderDetailinfoVo orderDetailinfo) {
        try {
            redisService.tokenValidate(orderDetailinfo.getToken());
            logger.info("EK>APP系统日志： 方法名[{}]客户进件项目填写[[{}]操作时间[{}]操作人[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),orderDetailinfo,DateUtils.getDateTime(),redisService.get(orderDetailinfo.getToken()));
            BeanUtils.beanValueTrim(orderDetailinfo);
            orderBaseinfoService.saveDetailinfo(orderDetailinfo);
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG);
        } catch (Exception e) {
            logger.error("ERROR EK>APP系统日志：方法名[{}]参数[{}]报错[{}]报错时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),orderDetailinfo,e.getMessage(), DateUtils.getDateTime());
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
    * @Description: TODO[ 客户附件上传 ]
    * @throws 
    */
    @RequestMapping(value = "saveOrderAttachment",method = RequestMethod.GET)
    public @ResponseBody Object saveOrderAttachment(String serverId,String token,String attchType,String orderId) {
        try {
            redisService.tokenValidate(token);
            logger.info("EK>APP系统日志：方法名[{}]客户附件上传[serverId{}attchType{}orderId{}]操作时间[{}]操作人[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),serverId,attchType,orderId,DateUtils.getDateTime(),redisService.get(token));
            Map<String, Object>res=orderBaseinfoService.saveOrderAttachment(serverId, token, attchType, orderId);
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG,res);
        } catch (Exception e) {
            logger.error("ERROR EK>APP系统日志：方法名[{}]参数[serverId{}attchType{}orderId{}] 报错[{}] 报错时间[{}]", Thread.currentThread().getStackTrace()[1].getMethodName(),serverId,attchType,orderId,e.getMessage(), DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE, e.getMessage());
        }
    }
    
    /** 
    * @Title: OrderBaseInfoController.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年6月27日 上午4:36:57  
    * @param @param vo
    * @param @return    设定文件 
    * @Description: TODO[ 客户签署CA ]
    * @throws 
    */
    @RequestMapping(value = "remoteSignCaOrder",method = RequestMethod.GET)
    public @ResponseBody Object remoteSignCaOrder(OrderBaseinfoVo vo) {
        try {
            redisService.tokenValidate(vo.getToken());
            logger.info("EK>APP系统日志：方法名[{}] 参数[{}]操作时间[{}]操作人[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),vo,DateUtils.getDateTime(),redisService.get(vo.getToken()));
            String url=orderBaseinfoService.remoteSignCaOrder(vo);
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG,url);
        } catch (Exception e) {
            logger.error("ERROR EK>APP系统日志：方法名[{}]参数[{}] 报错[{}]报错时间[{}]", Thread.currentThread().getStackTrace()[1].getMethodName(),vo.toString(),e.getMessage(),
                    DateUtils.getDateTime());
            e.printStackTrace();
            return ResultUtils.error(ResultUtils.ERROR_CODE, e.getMessage());
        }
    }
    
    /** 
    * @Title: OrderBaseInfoController.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年6月26日 下午11:54:28  
    * @param @param orderId
    * @param @param token
    * @param @return    设定文件 
    * @Description: TODO[ 客户签署协议预览]
    * @throws 
    */
    @RequestMapping(value = "previewCaOrder",method = RequestMethod.GET)
    public @ResponseBody Object previewCaOrder(String orderId,String token) {
        try {
            redisService.tokenValidate(token);
            logger.info("EK>APP系统日志： 方法名[{}]客户签署协议预览[orderId{}]操作时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),orderId,DateUtils.getDateTime());
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG,orderBaseinfoService.previewCaOrder(orderId));
        } catch (Exception e) {
            logger.error("ERROR EK>APP系统日志：方法名[{}]参数[{}] 报错[{}]报错时间[{}]", Thread.currentThread().getStackTrace()[1].getMethodName(),orderId,e.getMessage(),
                     DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE, e.getMessage());
        }
    }
    
    /** 
    * @Title: OrderBaseInfoController.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年6月27日 上午12:20:43  
    * @param @param token
    * @param @return    设定文件 
    * @Description: TODO[ 客户订单列表]
    * @throws 
    */
    @RequestMapping(value = "orderBaseinfos",method = RequestMethod.GET)
    public @ResponseBody Object orderBaseinfos(String token) {
        try {
            redisService.tokenValidate(token);
            logger.info("EK>APP系统日志： 方法名[{}]客户订单列表[token{}]操作时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),token,DateUtils.getDateTime());
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG,orderBaseinfoService.orderBaseinfos(token));
        } catch (Exception e) {
            logger.error("ERROR EK>APP系统日志：方法名[{}]参数[{}] 报错[{}] 报错时间[{}]", Thread.currentThread().getStackTrace()[1].getMethodName(), token,e.getMessage(),DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE, e.getMessage());
        }
    }
    
    /** 
    * @Title: OrderBaseInfoController.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年6月27日 上午2:49:04  
    * @param @param token
    * @param @param orderId
    * @param @return    设定文件 
    * @Description: TODO[ 客户订单详情]
    * @throws 
    */
    @RequestMapping(value = "orderBaseinfoDetail",method = RequestMethod.GET)
    public @ResponseBody Object orderBaseinfoDetail(String token,String orderId) {
        try {
            redisService.tokenValidate(token);
            logger.info("EK>APP系统日志：方法名[{}] 客户订单列表[token[{}]orderId[{}]]操作时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),token,orderId,DateUtils.getDateTime());
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG,orderBaseinfoService.orderBaseinfoDetail(orderId));
        } catch (Exception e) {
            logger.error("ERROR EK>APP系统日志：方法名[{}]参数[{}] 报错[{}] 报错时间[{}]", Thread.currentThread().getStackTrace()[1].getMethodName(),token,e.getMessage(),
                     DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE, e.getMessage());
        }
    }
    
    /** 
    * @Title: OrderBaseInfoController.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年6月27日 上午2:48:57  
    * @param @param token
    * @param @return    设定文件 
    * @Description: TODO[ 我要还款 ]
    * @throws 
    */
    @RequestMapping(value = "userReturnplans",method = RequestMethod.GET)
    public @ResponseBody Object userReturnplans(String token) {
        try {
            redisService.tokenValidate(token);
            logger.info("EK>APP系统日志： 方法名[{}]客户订单列表[token{}]操作时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),token,DateUtils.getDateTime());
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG,orderBaseinfoService.userReturnplans(token));
        } catch (Exception e) {
            logger.error("ERROR EK>APP系统日志：方法名[{}]参数[{}] 报错[{}] 报错时间[{}]", Thread.currentThread().getStackTrace()[1].getMethodName(),token,e.getMessage(),
                     DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE, e.getMessage());
        }
    }
}
