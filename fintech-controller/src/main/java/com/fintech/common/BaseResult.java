package com.fintech.common;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**   
* @Title: BaseResult.java 
* @Package com.medcfc.controller.common 
* @author qierkang xyqierkang@163.com   
* @date 2018年5月23日 下午10:37:41  
* @Description: TODO[ 用一句话描述该文件做什么 ]
*/
public class BaseResult {

    private String code;

    private String message;

    private Object data;

    public BaseResult(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public BaseResult(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public BaseResult(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public BaseResult(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseResult{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + ReflectionToStringBuilder.toString(data) +
                '}';
    }

}
