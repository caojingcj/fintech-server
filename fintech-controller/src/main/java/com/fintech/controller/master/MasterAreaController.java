package com.fintech.controller.master;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fintech.model.MasterArea;
import com.fintech.service.MasterAreaService;
import com.fintech.util.CommonUtil;
import com.fintech.util.DateUtils;
import com.fintech.util.result.BaseResult;
import com.fintech.util.result.ResultUtils;

/**   
* @Title: MasterAreaController.java 
* @Package com.fintech.controller.master 
* @author qierkang xyqierkang@163.com   
* @date 2018年6月12日 上午2:26:35  
* @Description: TODO[ 省市区主体控制类 ]
*/
@Controller
@RequestMapping(value = "/masterArea")
public class MasterAreaController {
    public static Logger logger = LoggerFactory.getLogger(MasterAreaController.class);

    @Autowired
    private MasterAreaService masterAreaService;
    
    @RequestMapping(value ="queryMasterAreaByKey")
    public @ResponseBody BaseResult queryMasterAreaByKey(MasterArea vo) {
        logger.info("EK 方法名[{}]操作时间[{}]操作人[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
        try {
            Map<String, Object>params=CommonUtil.object2Map(vo);
            return ResultUtils.success(ResultUtils.SUCCESS_CODE_MSG,masterAreaService.queryMasterAreaByKey(params));
        } catch (Exception e) {
            logger.info("ERROR EK参数[{}] 报错[{}] 方法名[{}]报错时间[{}]",vo,e.getMessage(),Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
            return ResultUtils.error(ResultUtils.ERROR_CODE,e.getMessage());
        }
    }
}
