package com.fintech.util;

/**   
* @Title: FinTechException.java 
* @Package com.fintech.util 
* @author qierkang xyqierkang@163.com   
* @date 2018年6月10日 下午11:26:43  
* @Description: TODO[ 用一句话描述该文件做什么 ]
*/
public class FinTechException extends RuntimeException{
    /** 
    * @Fields serialVersionUID : TODO[ 用一句话描述这个变量表示什么 ] 
    */ 
    private static final long serialVersionUID = 1L;

    private Object errorCode;
    

    private String errorMsg;

    public FinTechException(Object errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.errorMsg=message;
    }

    public FinTechException(String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
    }

    public FinTechException(String message, Throwable cause) {
        super(message, cause);
        this.errorMsg = message;
    }

    public Object getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
