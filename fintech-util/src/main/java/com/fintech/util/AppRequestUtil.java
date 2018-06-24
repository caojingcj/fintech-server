package com.fintech.util;

import com.alibaba.dubbo.common.utils.IOUtils;
import net.sf.json.JSONObject;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
/**   
* @Title: AppRequestUtil.java 
* @Package com.fintech.util 
* @author qierkang xyqierkang@163.com   
* @date 2018年6月23日 下午6:04:10  
* @Description: TODO[ 处理App请求的工具类 ]
*/
public final class AppRequestUtil {

    public static JSONObject getRequestBody(HttpServletRequest request) throws Exception{
        request.setCharacterEncoding("UTF-8");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String body = IOUtils.read(bufferedReader);
        JSONObject requestBody = JSONObject.fromObject(body);
        return requestBody;
    }

    public static JSONObject getRequestData(HttpServletRequest request) throws Exception{
        return getRequestBody(request).getJSONObject("data");
    }

}