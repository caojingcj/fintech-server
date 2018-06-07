/**
 * 
 */
package com.fintech.util.sign;

/**
 * @author  qierkang
 * @version  [版本号, 2017年3月17日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 *
 * @ClassName: ApiVo 
 * @Description: TODO< 这里用一句话描述这个类的作用 > 
 */
public class ApiVo {
	
	/** 系统生成的验证token */
	private String token;
	
	/**
	 * 获取系统生成的验证token
	 * @return 系统生成的验证token
	 */
	public String getToken() {
		return token;
	}
	
	/**
	 * 设置系统生成的验证token
	 * @param token 系统生成的验证token
	 */
	public void setToken(String token) {
		this.token = token;
	}
	
}
