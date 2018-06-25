package com.fintech.util.result;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**   
* @Title: BaseResult.java 
* @Package com.medcfc.controller.common 
* @author qierkang xyqierkang@163.com   
* @date 2018年5月23日 下午10:37:41  
* @Description: TODO[ 用一句话描述该文件做什么 ]
*/
public class BaseResult {

    private Object code;

    private Object message;
    
    private Object data;

    public BaseResult(Object code, Object message) {
        this.code = code;
        this.message = message;
    }

    public BaseResult(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public BaseResult(String message) {
        this.message = message;
    }


    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


    public Object getCode() {
        return code;
    }

    public void setCode(Object code) {
        this.code = code;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
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
