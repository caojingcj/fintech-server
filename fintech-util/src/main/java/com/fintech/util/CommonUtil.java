package com.fintech.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;

/**
 * 判断工具包
 *
 * @author DIY
 *
 */
public class CommonUtil {

	public static boolean isStrEmpty(Object value) {
		if (null == value) {
			return true;
		}
		return false;
	}

	public static boolean isNotStrEmpty(Object value) {
		if (null != value) {
			return true;
		}
		return false;
	}

	public static boolean isStrBlank(Object value) {
		if (null == value || value.toString().trim().equals("")) {
			return true;
		}
		return false;
	}

	public static boolean isNotStrBlank(Object value) {
		if (isStrBlank(value)) {
			return false;
		}
		return true;
	}

	public static boolean isNullStr(Object value) {
		if (null == value || value.toString().trim().equals("")
				|| value.toString().trim().toLowerCase().equals("null")) {
			return true;
		}
		return false;
	}

	public static boolean isNotNullStr(Object value) {
		if (isNullStr(value)) {
			return false;
		}
		return true;
	}

	public static boolean isNumber(Object value) {
		if (isNotNullStr(value)) {
			return value.toString().matches("^\\d+$");
		}
		return false;
	}

	/**
	 * 判断是否是金额，保留两位小数
	 *
	 * @param value
	 * @return
	 */
	public static boolean isMoney(Object value) {
		if (isStrBlank(value)) {
			return false;
		}
		Pattern pattern = Pattern.compile("^[\\-\\+]?(-)?[0-9]+(.[0-9]{1,2})?$"); // 判断小数点后2位的数字的正则表达式
		Matcher match = pattern.matcher(value.toString());
		return match.matches();
	}

	public static boolean isBigDecimal(Object value) {
		if (isStrBlank(value)) {
			return false;
		}
		Pattern pattern = Pattern.compile("^[\\-\\+]?(-)?[0-9]+(.[0-9]+)?$"); // 判断小数点后2位的数字的正则表达式
		Matcher match = pattern.matcher(value.toString());
		return match.matches();
	}

	public static <T extends Object> boolean isNotEmptyList(Collection<T> c) {
		if (null != c && c.size() > 0) {
			return true;
		}
		return false;
	}

	/** 判断是否是可用枚举 **/
	public static <T extends Enum<T>> boolean isEnum(Class<T> c, String value) {
		try {
			Enum.valueOf(c, value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static String getStringTime(Date date, String patter) {
		SimpleDateFormat format = new SimpleDateFormat(patter);
		return format.format(date);
	}

	public static String getUUIDString() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/** 删除map中值为null或者空字符的key **/
	public static void removeMapEmptyKey(Map<String, Object> map) {
		Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = it.next();
			if (CommonUtil.isStrBlank(map.get(entry.getKey()))) {
				it.remove();
			}
		}
	}

	public static <T extends Object> T compressObj(Object sourceObj, Class<T> c) {
		String str = JSON.toJSONString(sourceObj);
		return JSON.parseObject(str, c);
	}

	public static <T extends Object> List<Map<String,Object>> listToMap(List<T> list) {
		List<Map<String,Object>> resultMapList = new ArrayList<Map<String,Object>>();
		for(T t:list){
			resultMapList.add(JSON.parseObject(JSON.toJSONString(t)));
		}
		return resultMapList;
	}

	/**
	 * Object 转换为MAP
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> object2Map(Object obj) throws Exception {
		if(obj == null){
			return null;
		}
		
		
		//获取关联的所有类，本类以及所有父类
		boolean ret = true;
		Class oo = obj.getClass();
		List<Class> clazzs = new ArrayList<Class>();
		while(ret){
			clazzs.add(oo);
			oo = oo.getSuperclass();
			if(oo == null || oo == Object.class)break;
		}

		Map<String, Object> map = new HashMap<String, Object>();

		for(int i=0;i<clazzs.size();i++){
			Field[] declaredFields = clazzs.get(i).getDeclaredFields();
			for (Field field : declaredFields) {
				int mod = field.getModifiers();
				//过滤 static 和 final 类型
				if(Modifier.isStatic(mod) || Modifier.isFinal(mod)){
					continue;
				}
				field.setAccessible(true);
				map.put(field.getName(), field.get(obj));
			}
		}

		return map;
	}

	public static void main(String[] args) {
		System.out.println(isBigDecimal("23423.234324"));
	}

}
