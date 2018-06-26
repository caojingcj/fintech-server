package com.fintech.filter;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fintech.util.result.ResultUtils;
import com.google.gson.Gson;

/**   
* @Title: LogHandlerInterceptor.java 
* @Package com.fintech.filter 
* @author qierkang xyqierkang@163.com   
* @date 2018年6月12日 上午3:31:46  
* @Description: TODO[ FinTech拦截器 ]
*/
@Component
public class LogHandlerInterceptor implements HandlerInterceptor {
	
	private static Logger logger = LoggerFactory.getLogger(LogHandlerInterceptor.class);
	/**
	 * @Fields urls : TODO[ 设置白名单用户 ]
	 */
	private static String[] url = { "/user/login", "/user/loginOut", "/error" };
	public List<String>    urlList = Arrays.asList(url);
	
	
	/** 
	    * @Title: CommonInterceptor.java 
	    * @author qierkang xyqierkang@163.com   
	    * @date 2018年1月4日 下午7:44:52  
	    * @param @param req
	    * @param @param response
	    * @param @return
	    * @param @throws Exception    设定文件 
	    * @Description: TODO[ 无权限访问返回 ]
	    * @throws 
	    */
	    private boolean responseNoPerm(HttpServletRequest req,HttpServletResponse response) throws Exception{
	        PrintWriter out = null;
	        response.setContentType("application/json;charset=UTF-8");
	        out = response.getWriter();
	        out.print(ResultUtils.error( ResultUtils.ERROR_NO_PERM_CODE, ResultUtils.ERROR_NO_PERM_CODE_MSG));
	        out.flush();
	        return false;
	    }
	    
	    /** 
	     * @Title: CommonInterceptor.java 
	     * @author qierkang xyqierkang@163.com   
	     * @date 2018年1月4日 下午7:44:41  
	     * @param @param req
	     * @param @param response
	     * @param @return
	     * @param @throws Exception    设定文件 
	     * @Description: TODO[ 判断session是否失效 ]
	     * @throws 
	     */
	     private boolean sessionIsNull(HttpServletRequest req,HttpServletResponse response) throws Exception{
	         PrintWriter out = null;
	         response.setContentType("application/json;charset=UTF-8");
	         out = response.getWriter();
	         out.print(ResultUtils.error(ResultUtils.LOGIN_OUT_TIME, ResultUtils.LOGIN_OUT_TIME_MSG));
	         out.flush();
	         return false;
	     }
	    
	/**
	 **
	 * controller 执行之前调用
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		 if(request.getHeader("Origin") == null){
	            response.setHeader("Access-Control-Allow-Origin", "*");
	        }else if(request.getHeader("Origin").contains("16fenqi.com")||request.getHeader("Origin").contains("localhost")) {
	            response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
	        }else{
	            response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
	        }
	        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
	        response.setHeader("Access-Control-Max-Age", "3600");
	        response.setHeader("Access-Control-Allow-Credentials", "true");
	        response.setHeader("Access-Control-Allow-Headers", "Content-Type,Access-Token,x-requested-with");
//		logger.info("----------------进入拦截器-------------");
		Gson gson = new Gson();
//		if(permOpen== 0){
//			return true;
//		}else if(permOpen== 1){
//            String requestUri = request.getRequestURI();
//            String contextPath = request.getContextPath();
//            String url = requestUri.substring(contextPath.length()).indexOf(";") > -1
//                    ? requestUri.substring(contextPath.length()).substring(0,
//                    requestUri.substring(contextPath.length()).indexOf(";"))
//                    : requestUri.substring(contextPath.length());
//            if(urlList.contains(url)){
//                return true;
//            }
//            List<String> list = gson.fromJson(redisService.get("mer_url"),new TypeToken<List<String>>() {}.getType());
//            if(list == null || list.size()<=0){
//                return this.responseNoPerm(request,response);
//            }
//            if(list.contains(url)){
//                return true;
//            }else {
//                return this.responseNoPerm(request,response);
//            }
//        }
//		System.out.println("------preHandle执行之前调用-----");
//		return this.responseNoPerm(request,response);
		return true;
	}

	/**
	 * controller 执行之后，且页面渲染之前调用
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
//		System.out.println("------postHandle执行之后，且页面渲染之前调用-----");
	}

	/**
	 * 页面渲染之后调用，一般用于资源清理操作
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
//		System.out.println("------afterCompletion 页面渲染之后调用，一般用于资源清理操作-----");

	}

}
