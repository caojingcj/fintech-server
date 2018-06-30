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

import com.fintech.util.StringUtil;
import com.fintech.util.enumerator.ConstantInterface;
import com.fintech.util.result.ResultUtils;

import net.sf.json.JSONObject;

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
	private static String[] url = { "/app/weixin/wxCode", "/app/appLogin/appLogin", "/app/appLogin/appLoginVerification","/app/weixin/wxOpenId", "/error" };
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
	    private boolean redisIsNull(HttpServletRequest request,HttpServletResponse response) throws Exception{
	         logger.info("拦截器tokenIsNull>>>token[{}]",request.getParameter("token"));
	        PrintWriter out = null;
	        response.setContentType(ConstantInterface.Enum.CONTENT_TYPE.CONTENT_TYPE_APPLICATION_JSON.getValue());
	        out = response.getWriter();
	        out.print(JSONObject.fromObject(ResultUtils.error(ConstantInterface.Enum.ObjectNullValidate.OBJECT_REDIS_KEY_99912.toString())));
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
    	    String requestUri = request.getRequestURI();
            String contextPath = request.getContextPath();
            String url = requestUri.substring(contextPath.length()).indexOf(";") > -1
                    ? requestUri.substring(contextPath.length()).substring(0,
                    requestUri.substring(contextPath.length()).indexOf(";"))
                    : requestUri.substring(contextPath.length());
            if(urlList.contains(url)){
                return true;
            }
//            if(StringUtil.isEmpty(request.getParameter("token"))) {
//                return redisIsNull(request, response);
//            }
//            if(StringUtil.isEmpty(request.getParameter("token").replace(" ", "").replace("null", ""))) {
//                return redisIsNull(request, response);
//            }
            //前后台分离 request 后面会拿不到参数 需要研究下
	        logger.info("----------------拦截器token-------------");
//	        Gson gson = new Gson();
//		if(permOpen== 0){
//			return true;
//		}else if(permOpen== 1){
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
