package com.fintech.controller.login;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fintech.common.properties.AppConfig;
import com.fintech.model.CompanyBaseinfo;
import com.fintech.model.CompanyChannel;
import com.fintech.model.CompanyItem;
import com.fintech.model.CompanyPeriodFee;
import com.fintech.model.vo.LogSmsMessageVo;
import com.fintech.service.CompanyBaseinfoService;
import com.fintech.service.CompanyChannelService;
import com.fintech.service.CompanyItemService;
import com.fintech.service.CompanyPeriodFeeService;
import com.fintech.service.LogSmsMessageService;
import com.fintech.service.RedisService;
import com.fintech.util.CommonUtil;
import com.fintech.util.DateUtils;
import com.fintech.util.StringUtil;
import com.fintech.util.enumerator.ConstantInterface;
import com.fintech.util.result.BaseResult;
import com.fintech.util.result.ResultUtils;

@Controller
@RequestMapping(value = "app/appLogin")
public class AppLoginController {
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
    private String action="code";//发送短信
    public static Logger logger = LoggerFactory.getLogger(AppLoginController.class);

    
    /** 
    * @Title: AppLoginController.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年6月25日 上午2:21:26  
    * @param @param mobile
    * @param @return    设定文件 
    * @Description: TODO[ 登录注册 ]
    * @throws 
    */
    @RequestMapping(value ="appLoginRegister")
    public @ResponseBody BaseResult appLogin(String mobile) {
        logger.info("EK 方法名[{}]操作时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
        try {
            String sendCode=logSmsMessageService.generateVerifyCode(mobile, 4);
//            Map<String, Object>params=CommonUtil.object2Map(vo);
//            Map<String, Object>params=new HashMap<String, Object>();
//            params.put("companyId", "999999");
            //拿商户999999测试 generateVerifyCode
            String companyId="999999";
            CompanyBaseinfo baseinfo=companyBaseinfoService.selectByPrimaryKeyInfo(companyId);
            if(baseinfo.getCompanyStatus().equals(ConstantInterface.Enum.ConstantNumber.ZERO.getKey().toString())) {
                return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG,"该商户已被禁用");
            }
            if(!StringUtil.isEmpty(redisService.get(mobile))) {
                //用户已经有过记录 直接跑出商户信息
                Map<String, Object>parms=new HashMap<>();
                parms.put("companyId", companyId);
                List<CompanyChannel> channels= companyChannelService.selectByPrimaryKeyList(parms);//咨询师
                List<CompanyPeriodFee> periodFees=companyPeriodFeeService.selectByPrimaryKeyList(parms);//费率
                List<CompanyItem> items= companyItemService.selectByPrimaryKeyList(parms);//项目
                Map<String, Object>reslutMap=new HashMap<>();
                reslutMap.put("channels", channels);
                reslutMap.put("periodFees", periodFees);
                reslutMap.put("items", items);
                reslutMap.put("baseinfo", baseinfo);
                return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG,reslutMap);
            }
            redisService.setVal(action+mobile,sendCode, 60 * 10); //放入redis 10分钟有效期
            LogSmsMessageVo record=new LogSmsMessageVo();
            record.setSendMobile(mobile);
            record.setSendCode(sendCode);
            logSmsMessageService.sendSmsMessage(record);
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG);
        } catch (Exception e) {
            logger.error("ERROR EK参数[{}] 报错[{}] 方法名[{}]报错时间[{}]",mobile,Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
        }
    }
    
    @RequestMapping(value ="appLoginVerification")
    public @ResponseBody BaseResult appLoginVerification(String mobile,String code) {
        logger.info("EK 方法名[{}]操作时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
        try {
            String getCode=redisService.get(action+mobile); //放入redis 10分钟有效期
            if(!getCode.equals(code)) {
                return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG,"验证码错误或失效！");
            }
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG);
        } catch (Exception e) {
            logger.error("ERROR EK参数[{}] 报错[{}] 方法名[{}]报错时间[{}]",mobile,Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
        }
    }
    
}
