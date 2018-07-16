package com.fintech.util;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 处理响应结果到页面
 */
public final class ResponseUtil {
    private static final Logger logger = LoggerFactory.getLogger(ResponseUtil.class);

    /**
     * angular js 模型分离返回
     */
    public static final Integer METHOD_SEPARATE_ANGULAR_JAVASCRIPT = 1;

    /**
     * 给页面返回一个Object
     * 实际实现方式是把Object装成JSON
     * 然后再进行toString操作
     *
     * @param response HttpServletResponse
     * @param object   需要给页面的实体
     */
    public static void setOutputObject(HttpServletResponse response, Object object) {
        try {
            assert object == null : "entity is not null!";
            response.setCharacterEncoding("UTF-8");
            response.getWriter().append(JSONObject.fromObject(object).toString());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("out object error", e);
        }
    }

    /**
     * 给页面返回一个Array
     * 实际实现方式是把Array装成JSON
     * 然后再进行toString操作
     *
     * @param response HttpServletResponse
     * @param object   需要给页面的实体
     */
    public static void setOutputArray(HttpServletResponse response, Object object) {
        try {
            assert object == null : "entity is not null!";
            response.setCharacterEncoding("UTF-8");
            response.getWriter().append(JSONArray.fromObject(object).toString());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("out array error", e);
        }
    }

    /**
     * 给一个返回一个String
     *
     * @param response HttpServletResponse
     * @param arg      字符
     */
    public static void setOutputString(HttpServletResponse response, String arg) {
        try {
            assert StringUtils.isBlank(arg) : "str is not null!";
            response.setCharacterEncoding("UTF-8");
            response.getWriter().append(arg);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("out string error", e);
        }
    }

    /**
     * 前后端分离需要特殊的返回方式，因为受到JSON跨域的问题
     *
     * @param response       HttpServletResponse
     * @param object         需要返回的对象
     * @param callback       跨域请求页面带的值
     * @param methodSeparate JavaScript类型
     *
     * @author luoyangwei
     * @date 2017/08/02
     * @modify 2017/10/25 加了一个逻辑，如果JSONObject 转型错误，就使用JSONArray尝试
     */
    public static void setOutputObject(HttpServletResponse response, Object object, String callback,
                                       Integer methodSeparate) {
        assert object == null : "entity is not null!";
        try {
            if (METHOD_SEPARATE_ANGULAR_JAVASCRIPT.equals(methodSeparate)) {
                response.setCharacterEncoding("UTF-8");
                try {
                    response.getWriter().append(callback + "(" + JSONObject.fromObject(object).toString() + ")");
                } catch (Exception e) {
                    response.getWriter().append(callback + "(" + JSONArray.fromObject(object).toString() + ")");
                }
            }
        } catch (Exception e) {
            logger.error("out object error", e);
        }
    }
}
