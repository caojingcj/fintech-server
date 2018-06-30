package com.fintech.util.sign;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author  qierkang
 * @version  [版本号, 2017年3月17日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 *
 * @ClassName: ParamSignUtils 
 * @Description: TODO< 在http接口对参数做签名，防止接口被非法调用 > 
 */
public class ParamSignUtils {
	
//	private static String secret=ResourceUtil.geteString("sgin.secret");
	private static String secret="t7j";
	public static void main(String[] args)  
    {  
        HashMap<String, String> signMap = new HashMap<String, String>();  
        signMap.put("token","2XWJMHVovs2zKYXGrHrciuzFONJl-9LQ");
        signMap.put("timestamp","1489981232");
        signMap.put("nonce","3.1415926789");
        HashMap SignHashMap=ParamSignUtils.sign(signMap);
        System.out.println("SignHashMap:"+SignHashMap);
        HashMap SignHashMap2=ParamSignUtils.sign(signMap);
        System.out.println("SignHashMap2:"+SignHashMap2);
        
    }  
  
//    public static HashMap<String, String> sign(Map<String, String> paramValues) {  
//        return sign(paramValues, null, "");  
//    }  
  
	public static String sign(String decript) {  
	    try {  
	        MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");  
	        digest.update(decript.getBytes());  
	        byte messageDigest[] = digest.digest();  
	        // Create Hex String  
	        StringBuffer hexString = new StringBuffer();  
	        // 字节数组转换为 十六进制 数  
	            for (int i = 0; i < messageDigest.length; i++) {  
	                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);  
	                if (shaHex.length() < 2) {  
	                    hexString.append(0);  
	                }  
	                hexString.append(shaHex);  
	            }  
	            return hexString.toString();  
	   
	        } catch (NoSuchAlgorithmException e) {  
	            e.printStackTrace();  
	        }  
	        return "";  
	}  
	
    /** 
     * @param paramValues 
     * @param ignoreParamNames 
     * @param secret 
     * @return 
     */  
    public static HashMap<String, String> sign(Map<String, String> paramValues) {  
        try {  
            HashMap<String, String> signMap = new HashMap<String, String>();  
            StringBuilder sb = new StringBuilder();  
            List<String> paramNames = new ArrayList<String>(paramValues.size());  
            paramNames.addAll(paramValues.keySet());  
//            if (ignoreParamNames != null && ignoreParamNames.size() > 0) {  
//                for (String ignoreParamName : ignoreParamNames) {  
//                    paramNames.remove(ignoreParamName);  
//                }  
//            }  
            Collections.sort(paramNames);  
            sb.append(secret);  
            for (String paramName : paramNames) {  
                sb.append(paramName).append(paramValues.get(paramName));  
            }  
            sb.append(secret);  
            byte[] md5Digest = getMD5Digest(sb.toString());  
            String sign = byte2hex(md5Digest);  
            signMap.put("param", sb.toString());  
            signMap.put("sign", sign);  
            return signMap;  
        } catch (IOException e) {  
            throw new RuntimeException("加密签名计算错误", e);  
        }  
          
    }  
  
    public static String utf8Encoding(String value, String sourceCharsetName) {  
        try {  
            return new String(value.getBytes(sourceCharsetName), "UTF-8");  
        } catch (UnsupportedEncodingException e) {  
            throw new IllegalArgumentException(e);  
        }  
    }  
  
    private static byte[] getSHA1Digest(String data) throws IOException {  
        byte[] bytes = null;  
        try {  
            MessageDigest md = MessageDigest.getInstance("SHA-1");  
            bytes = md.digest(data.getBytes("UTF-8"));  
        } catch (GeneralSecurityException gse) {  
            throw new IOException(gse);  
        }  
        return bytes;  
    }  
  
    private static byte[] getMD5Digest(String data) throws IOException {  
        byte[] bytes = null;  
        try {  
            MessageDigest md = MessageDigest.getInstance("MD5");  
            bytes = md.digest(data.getBytes("UTF-8"));  
        } catch (GeneralSecurityException gse) {  
            throw new IOException(gse);  
        }  
        return bytes;  
    }  
  
  
    private static String byte2hex(byte[] bytes) {  
        StringBuilder sign = new StringBuilder();  
        for (int i = 0; i < bytes.length; i++) {  
            String hex = Integer.toHexString(bytes[i] & 0xFF);  
            if (hex.length() == 1) {  
                sign.append("0");  
            }  
            sign.append(hex.toUpperCase());  
        }  
        return sign.toString();  
    }  
}
