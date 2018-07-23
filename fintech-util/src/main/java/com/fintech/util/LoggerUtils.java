package com.fintech.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtils {
    private static final Logger logger = LoggerFactory.getLogger(LoggerUtils.class);

    /** 
    * @Title: LoggerUtils.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2017年8月3日 下午3:58:49  
    * @param @param mes
    * @param @param e    设定文件 
    * @Description: TODO[ 封装error打印 ]
    * @throws 
    */
    public static void getLoggerError(String mes, Exception e) {
	logger.error(mes + ">>>类[{}]方法:[{}]行数:[{}]>>>具体报错:[{}]", Thread.currentThread().getStackTrace()[0].getFileName(), Thread.currentThread().getStackTrace()[0].getMethodName(),
		Thread.currentThread().getStackTrace()[0].getLineNumber(), e.getMessage(),e);
    }
    
}
