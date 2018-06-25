package com.fintech.util.result;

/**   
* @Title: ResultUtils.java 
* @Package com.medcfc.controller.common 
* @author qierkang xyqierkang@163.com   
* @date 2018年5月23日 下午10:35:59  
* @Description: TODO[ 用一句话描述该文件做什么 ]
*/
public class ResultUtils {

    public static final  String SUCCESS ="0";

    public static final  String FAIL ="-1";

    public static final String SUCCESS_CODE = "000000";
    
    public static final String SUCCESS_CODE_MSG = "操作成功";
    
    public static final String SUCCESS_CODE_SEL_MSG = "查询成功";

    public static final String ERROR_NO_PERM_CODE = "000001";

    public static final String ERROR_NO_PERM_CODE_MSG = "无权限访问";

    public static final String LOGIN_OUT_TIME = "000100";

    public static final String LOGIN_OUT_TIME_MSG = "登陆超时，请重新登陆";

    public static final String ERROR_CODE = "999999";

    public static final String ERROR_CODE_MSG = "操作失败";


    /**
     * 成功返回
     * @param message
     * @return
     */
    public static BaseResult successResulet(String message){
        return  new BaseResult(SUCCESS,message);
    }

    /**
     * 成功返回
     * @param message
     * @param data
     * @return
     */
    public static BaseResult successResulet(String message,Object data){
        return  new BaseResult(SUCCESS,message,data);
    }

    /***
     * 失败返回
     * @param message
     * @return
     */
    public static BaseResult failResulet(String message){
        return  new BaseResult(SUCCESS,message);
    }



    /**
     * 成功返回
     * @param message
     * @return
     */
    public static BaseResult success(String message){
        return  new BaseResult(SUCCESS_CODE,message);
    }
    /**
     * 成功返回
     * @param message
     * @return
     */
    public static BaseResult success(String message,Object data){
        return  new BaseResult(SUCCESS_CODE,message,data);
    }

    /***
     * 失败返回
     * @param message
     * @return
     */
    public static BaseResult error(String message){
        return  new BaseResult(ERROR_CODE,message);
    }

    /***
     * 失败返回
     * @param message
     * @return
     */
    public static BaseResult error(String code,String message){
        return  new BaseResult(code,message);
    }
    
    /***
     * 失败返回
     * @param message
     * @return
     */
    public static BaseResult error(Object code,Object message){
        return  new BaseResult(code,message);
    }
    /***
     * 失败返回
     * @param message
     * @return
     */
    public static BaseResult error(String message,Object vo){
        return  new BaseResult(ERROR_CODE,message,vo);
    }
}
