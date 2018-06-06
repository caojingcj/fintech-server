package com.fintech.util.sign;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

public class TokenUtil {
	
    private static final Logger logger = LoggerFactory.getLogger(TokenUtil.class);

	private static final String[] codeBase= {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
	private static Random rand= new Random();
	/** XXTEA加密解密的密钥 */
	private static String secKey = "captcha";
	
	/** 验证码超时门限(秒) */
	public static long expire = 30;
	
	/** 缓存前缀 */
	private static String cachePrefix = "captcha";
	
	/** 验证字符数 */
	private static int charCount = 6;
	
	public static String getToken() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < charCount; i++) {
			int randInt = Math.abs(rand.nextInt());
			sb.append(codeBase[randInt % codeBase.length]);
		}
		long timestamp = System.currentTimeMillis();
		String token = null;
		token = String.format("%s_%d", sb.toString(), timestamp);
		logger.info("未加密的token:" +token+"---"+secKey);
		token = XXTEAUtil.encrypt(token, secKey);
		return token;
	}
	
	/**
	 * 生成token已验证key
	 * @param token
	 * @return
	 */
	public static String genVerifiedTokenCacheKey(String token){
		return String.format("%s_token_verified_%s", cachePrefix, token);
	}
	
	
	public static String getCaptcha(String token){
		try{
			String plainText= XXTEAUtil.decrypt(token, secKey);
			if (StringUtils.isBlank(plainText)){
				return JSON.toJSONString(new TokenUtil().new ResultSignMes(SignEnum.Code.CODE_70009.getKey(), SignEnum.Code.CODE_70009.getValue()));
			}
			String[] plainTextArr= plainText.split("_");
			if (plainTextArr.length!=2){
				return JSON.toJSONString(new TokenUtil().new ResultSignMes(SignEnum.Code.CODE_70008.getKey(), SignEnum.Code.CODE_70008.getValue()));
			}
			long timestamp= 0;
			try{
				timestamp= Long.parseLong(plainTextArr[1]);
			}catch(NumberFormatException e){
				return JSON.toJSONString(new TokenUtil().new ResultSignMes(SignEnum.Code.CODE_70008.getKey(), SignEnum.Code.CODE_70008.getValue()));
			}
			if ((System.currentTimeMillis() - timestamp)>TimeUnit.MILLISECONDS.convert(expire+5, TimeUnit.SECONDS)){
				return JSON.toJSONString(new TokenUtil().new ResultSignMes(SignEnum.Code.CODE_70006.getKey(), SignEnum.Code.CODE_70006.getValue()));
			}
		}catch(Exception e){
			return JSON.toJSONString(new TokenUtil().new ResultSignMes(SignEnum.Code.CODE_70005.getKey(), SignEnum.Code.CODE_70005.getValue()));
		}
		return JSON.toJSONString(new TokenUtil().new ResultSignMes(SignEnum.Code.CODE_70004.getKey(), SignEnum.Code.CODE_70004.getValue()));
	}
	
	class ResultSignMes{
		private Integer code;
		private String mes;
		public Integer getCode() {
			return code;
		}
		public void setCode(Integer code) {
			this.code = code;
		}
		public String getMes() {
			return mes;
		}
		public void setMes(String mes) {
			this.mes = mes;
		}
		public ResultSignMes(Integer code, String mes) {
			super();
			this.code = code;
			this.mes = mes;
		}
		@Override
		public String toString() {
			return "resultSignMes [code=" + code + ", mes=" + mes + "]";
		}
	}
	
	public static void main(String[] args) {
		TokenUtil tokenUtil=new TokenUtil();
		System.out.println(tokenUtil.getToken());
		
//		String plainText=tokenUtil.getToken(); 
//		String[] plainTextArr= plainText.split("_");
//		System.out.println(plainTextArr.length);
	}
}
