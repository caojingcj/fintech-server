package com.fintech.util;

import java.util.Map;

/**   
* @Title: MapUtils.java 
* @Package com.medcfc.utils 
* @author qierkang xyqierkang@163.com   
* @date 2018年3月18日 下午6:20:01  
* @Description: TODO[ 用一句话描述该文件做什么 ]
*/
public class MapUtils {

	public static String getMap2String(Map<String, Object> map,Object key){
        Object value =  map.get(key);
        if(value==null){
            return "";
        }
        return String.valueOf(value);
    }

}
