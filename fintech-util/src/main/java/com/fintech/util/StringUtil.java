package com.fintech.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.RandomUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 字符串的工具类<br>
 * TODO 这里面这么有些不该在这里面的方法
 * 
 * @author yuqih
 * 
 */
public class StringUtil {
	final static String METHOD_SET = "set";
	final static String METHOD_GET = "get";

	private static final String SEP1 = ",";
	private static final String SEP2 = "|";
	private static final String SEP3 = "=";

	/**
	 * 笨方法：String s = "你要去除的字符串"; <br>
	 * 1.去除空格：s = s.replace('\\s',''); <br>
	 * 2.去除回车：s =s.replace('\n',''); 这样也可以把空格和回车去掉，其他也可以照这样做。<br>
	 * 注：\n 回车 \t 水平制表符 \s空格 \r 换行
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	public static String firstCharUpperCase(String s) {
		StringBuffer sb = new StringBuffer(s.substring(0, 1).toUpperCase());
		sb.append(s.substring(1, s.length()));
		return sb.toString();

	}

	/**
	 * 将特殊字符转义再切分
	 * 
	 * @param source
	 * @param spliter
	 * @return
	 */
	public static String[] splitStr2Array(String source, String spliter) {

		String regex = spliter;
		if (regex.equals("?") || regex.equals("*") || regex.equals(")") || regex.equals("(") || regex.equals("{")
				|| regex.equals("$") || regex.equals("+") || regex.equals(".") || regex.equals("|")) {

			regex = "[" + regex + "]";
		}

		return source.split(regex);
	}

	/**
	 * 将byte[] 转换成字符串
	 * 
	 * @param b
	 * @return
	 */
	public static String byte2Hex(byte[] srcBytes) {
		StringBuilder hexRetSB = new StringBuilder();
		for (byte b : srcBytes) {
			String hexString = Integer.toHexString(0x00ff & b);
			hexRetSB.append(hexString.length() == 1 ? 0 : "").append(hexString);
		}
		return hexRetSB.toString();
	}

	/**
	 * 将16进制字符串转为转换成字符串
	 * 
	 * @param source
	 * @return
	 */
	public static byte[] hex2Bytes(String source) {
		byte[] sourceBytes = new byte[source.length() / 2];
		for (int i = 0; i < sourceBytes.length; i++) {
			sourceBytes[i] = (byte) Integer.parseInt(source.substring(i * 2, i * 2 + 2), 16);
		}
		return sourceBytes;
	}

	/**
	 * 如果给定字符串为null,则返回空
	 * 
	 * @param text
	 * @return
	 */
	public static String formatString(String text) {
		if (text == null) {
			return "";
		}
		return text;
	}

	/**
	 * 判断字符串是否有数字串加上特定的分隔符组成 如:1,2,3
	 * 
	 * @param value
	 * @param seperator
	 * @return
	 */
	public static boolean isNumberSepString(String value, String seperator) {

		boolean result = true;
		String[] arr = value.split(seperator);
		for (int i = 0; i < arr.length; i++) {

			if (!isNumeric(arr[i]))
				return false;
		}
		return result;
	}

	/**
	 * 判断给定字符串是否是数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 首字母大写
	 * 
	 * @param string
	 * @return
	 */
	public static String upperFirstChar(String string) {
		StringBuilder sb = new StringBuilder();
		sb.append((char) (string.charAt(0) - 32)).append(string.substring(1));
		return sb.toString();
	}

	/**
	 * @param fieldName
	 * @return
	 */
	public static String genGetMethodName(String fieldName) {
		StringBuilder sb = new StringBuilder();
		sb.append(METHOD_GET).append(StringUtil.upperFirstChar(fieldName));
		return sb.toString();
	}

	private static char[] RANDOM_CHARS = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c',
			'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
			'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
			'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	public static String randomString(int length) {
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = RANDOM_CHARS[RandomUtils.nextInt(0, RANDOM_CHARS.length)];
		}
		return new String(randBuffer);
	}

	public static String parseDomain(String requestUrl) {
		int eIdx = requestUrl.length();
		if (requestUrl.startsWith("http://")) {
			requestUrl = requestUrl.substring(7);
		} else if (requestUrl.startsWith("https://")) {
			requestUrl = requestUrl.substring(8);
		}
		eIdx = requestUrl.indexOf("/");
		return requestUrl.substring(0, eIdx);
	}

	/*
	 * 判断是否为空 true 为空，false不为空
	 */
	public static boolean isEmpty(String obj) {
		return obj == null || obj.trim().length() == 0;
	}

	/** 
	* @Title: StringUtil.java 
	* @author qierkang xyqierkang@163.com   
	* @date 2017年8月20日 下午11:27:50  
	* @param @param obj
	* @param @return    设定文件 
	* @Description: TODO[ 判断Object对象为空  ]
	* @throws   判断是否为空 true 为空，false不为空
	*/
	public static boolean isEmpty(Object obj) {
		if (obj == null) {
			return true;
		}
		if ((obj instanceof List)) {
			return ((List<?>) obj).size() == 0;
		}
		if ((obj instanceof String)) {
			return ((String) obj).trim().equals("");
		}
		return false;
	}

	/**
	 * 判断该字符串是否为中文
	 * @param string
	 * @return
	 */
	public static boolean isChinese(String string) {
		int n = 0;
		for (int i = 0; i < string.length(); i++) {
			n = (int) string.charAt(i);
			if (!(19968 <= n && n < 40869)) {
				return false;
			}
		}
		return true;
	}

	/** 
	* @Title: StringUtil.java 
	* @author qierkang xyqierkang@163.com   
	* @date 2018年5月23日 上午8:15:53  
	* @param @param list
	* @param @return    设定文件 
	* @Description: TODO[ 这里用一句话描述这个方法的作用 ]
	* @throws 
	*/
	public static String ListToString(List<?> list) {
		StringBuffer sb = new StringBuffer();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) == null || list.get(i) == "") {
					continue;
				}
				if (list.get(i) instanceof List) {
					sb.append(ListToString((List<?>) list.get(i)));
					sb.append(SEP1);
				} else if (list.get(i) instanceof Map) {
					sb.append(MapToString((Map<?, ?>) list.get(i)));
					sb.append(SEP1);
				} else {
					sb.append(list.get(i));
					sb.append(SEP1);
				}
			}
		}
		return "L" + sb.toString();
	}

	/** 
	* @Title: StringUtil.java 
	* @author qierkang xyqierkang@163.com   
	* @date 2018年5月23日 上午8:15:56  
	* @param @param map
	* @param @return    设定文件 
	* @Description: TODO[ 这里用一句话描述这个方法的作用 ]
	* @throws 
	*/
	public static String MapToString(Map<?, ?> map) {
		StringBuffer sb = new StringBuffer();
		for (Object obj : map.keySet()) {
			if (obj == null) {
				continue;
			}
			Object key = obj;
			Object value = map.get(key);
			if (value instanceof List<?>) {
				sb.append(key.toString() + SEP1 + ListToString((List<?>) value));
				sb.append(SEP2);
			} else if (value instanceof Map<?, ?>) {
				sb.append(key.toString() + SEP1 + MapToString((Map<?, ?>) value));
				sb.append(SEP2);
			} else {
				sb.append(key.toString() + SEP3 + value.toString());
				sb.append(SEP2);
			}
		}
		return "M" + sb.toString();
	}

	public static List<Object> StringToList(String listText) {
		if (listText == null || listText.equals("")) {
			return null;
		}
		listText = listText.substring(1);
		List<Object> list = new ArrayList<>();
		String[] text = listText.split("\\" + SEP1);
		String listStr = "";
		boolean flag = false;
		for (String str : text) {
			if (!str.equals("")) {
				if (str.charAt(0) == 'M') {
					Map<?, ?> map = StringToMap(str);
					list.add(map);
				} else if (str.charAt(0) == 'L' || flag) {
					flag = true;
					listStr += str + SEP1;
				} else {
					list.add(str);
				}
			}
			if (str.equals("")) {
				flag = false;
				List<?> lists = StringToList(listStr);
				list.add(lists);
			}
		}
		return list;
	}

	public static Map<String, Object> StringToMap(String mapText) {
		if (mapText == null || mapText.equals("")) {
			return null;
		}
		mapText = mapText.substring(1);
		Map<String, Object> map = new HashMap();
		String[] text = mapText.split("\\" + SEP2); // 转换为数组
		for (String str : text) {
			String[] keyText = str.split(SEP3); // 转换key与value的数组
			if (keyText.length < 1) {
				continue;
			}
			String key = keyText[0]; // key
			String value = keyText[1]; // value
			if (value.charAt(0) == 'M') {
				Map<?, ?> map1 = StringToMap(value);
				map.put(key, map1);
			} else if (value.charAt(0) == 'L') {
				List<?> list = StringToList(value);
				map.put(key, list);
			} else {
				map.put(key, value);
			}
		}
		return map;
	}

	public static void main(String[] args) {

		String ss = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ~!@#$%^&*()_+{}|:>?;'./[]-=`'";
		char[] numbersAndLetters = ss.toCharArray();
		for (char c : numbersAndLetters) {
			System.out.print("'" + c + "',");
		}
		System.out.println(isEmpty(new Object()));
	}

	// 获取json里的数据
	public static String toJson(String str, String key) {
		String s = "";
		if (str != "" && str != null) {
			Gson gson = new Gson();
			List<Map<String, String>> list = gson.fromJson(str, new TypeToken<List<Map<String, String>>>() {
			}.getType());
			for (int i = 0; i < list.size(); i++) {
				Map<String, String> map = list.get(i);
				if (map != null) {
					if (map.get("key").toString().equals(key)) {
						s = map.get("value");
						break;
					}
				}
			}
		}
		return s;
	}
}
