package com.fintech.datasource.druid;

import com.alibaba.druid.support.http.StatViewServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 * @author Quinn
 * @date 2018/5/31
 * @package com.qiaorong.app.filter
 */
@WebServlet(urlPatterns = "/druid/*",
        initParams = {
                @WebInitParam(name = "allow", value = "127.0.0.1"),// IP白名单 (没有配置或者为空，则允许所有访问)
//                @WebInitParam(name = "deny", value = "192.168.16.111"),// IP黑名单 (存在共同时，deny优先于allow)
                @WebInitParam(name = "loginUsername", value = "admin"),// 用户名
                @WebInitParam(name = "loginPassword", value = "qierkang"),// 密码
                @WebInitParam(name = "resetEnable", value = "false")// 禁用HTML页面上的“Reset All”功能
        })
public class DruidServlet extends StatViewServlet  {

	/** 
	* @Fields serialVersionUID : TODO[ 用一句话描述这个变量表示什么 ] 
	*/ 
	private static final long serialVersionUID = 1L;
}
