package com.fintech.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import net.sf.json.JSONObject;


public class HttpClient {

	private static Logger logger = LoggerFactory.getLogger(HttpClient.class);

	private static final String DEFAULT_CHARSET = "UTF-8";
	private static final String APPLICATION_JSON = "application/json";
	private static final String CONTENT_TYPE_TEXT_JSON = "application/x-www-form-urlencoded;charset=UTF-8";
	private static final String CONTENT_JSON = "application/json; charset=utf-8";
	private static final String CONTENT_PLAIN = "text/plain;charset=utf-8";
	private static long startTime = 0L;
	private static long endTime = 0L;

    public static String jsonPost(String url, Map<String, Object> params) {
        com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject(params);
        String reqStr = json.toJSONString();
        String respStr = post(url, reqStr,CONTENT_JSON,params);
        return respStr;
    }
    
    public static String jsonPost(String url, String json,Map<String, Object>map) {
        String respStr = post(url, json,CONTENT_JSON,map);
        return respStr;
    }

	public static String jsonPostObj(String url, Object params) {
		String respStr = null;
		try {
			respStr = jsonPost(url, ObjectUtil.object2Map(params));
			return respStr;
		} catch (Exception e) {
			logger.error("HTTP 请求异常",e);
		}
		return null;
	}

	/**
	 * http post请求 
	 * 
	 * @param url
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @return
	 */
	public static net.sf.json.JSONObject post(String url,String key,String json) {
		logger.info("httpclient POST url={},data={}",url,json);
		try {
		    CloseableHttpClient httpClient = HttpClients.createDefault();
		    MultipartEntity multipartMode = new MultipartEntity(HttpMultipartMode.STRICT,
	                    "application/x-www-form-urlencoded", Charset.forName(DEFAULT_CHARSET));
		    HttpPost method = new HttpPost(url);
		    if (json != null) {
			multipartMode.addPart("key", new StringBody(key.trim()));
			multipartMode.addPart("data", new StringBody(json,Charset.forName(DEFAULT_CHARSET)));
		    }
	            startTime = System.currentTimeMillis(); 
	            method.setEntity(multipartMode);
	            HttpResponse response = httpClient.execute(method);
	            endTime = System.currentTimeMillis();
//	            logger.info("调用API 花费时间(单位：毫秒)：" + (endTime - startTime));
	            net.sf.json.JSONObject JSONObject = handleResponse(response);
		    logger.info("POST请求服务器返回:[{}]",JSONObject);
			if (JSONObject != null) return JSONObject;
		} catch (Exception e) {
			logger.error("HTTP请求异常",e);
		}
		return null;
	}
	


	private static String getResultStr(String url, CloseableHttpResponse response) throws IOException {
		try {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity, DEFAULT_CHARSET);
//                logger.info("HTTP请求返回:url={},result={}",url,result);
                return result;
            }
        } finally {
            response.close();
        }
		return null;
	}

	/**
	 * http post请求
	 * 
	 * @param url
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @return
	 */
	@SuppressWarnings("all")
	    public static net.sf.json.JSONObject post(String url, String httpKey, net.sf.json.JSONObject jsonObject, File[] files) {
	        //post请求返回结果
	        DefaultHttpClient httpClient = new DefaultHttpClient();
	        net.sf.json.JSONObject jsonResult = new net.sf.json.JSONObject();
	        HttpPost method = new HttpPost(url);
	        try {
	            if (jsonObject != null) {
	                MultipartEntity multipartMode = new MultipartEntity(HttpMultipartMode.STRICT,
	                        "application/x-www-form-urlencoded", Charset.forName(DEFAULT_CHARSET));
	                for (int i = 0; i < files.length; i++) {
	                    FileBody fileBody = new FileBody(files[i]);
	                    multipartMode.addPart("file", fileBody);
	                }
	                multipartMode.addPart("key", new StringBody(httpKey.trim()));
	                multipartMode.addPart("data", new StringBody(jsonObject.toString()));
	                method.setEntity(multipartMode);
	            }
	            HttpResponse response = httpClient.execute(method);
	            jsonResult = handleResponse(response);
	        } catch (Exception e) {
	            logger.error("POST请求提交失败  {}", e);
	        }
	        return jsonResult;
	    }
	
	 public static net.sf.json.JSONObject handleResponse(HttpResponse response) {
	        net.sf.json.JSONObject jsonResult = new net.sf.json.JSONObject();
	        /**请求发送成功，并得到响应**/
	        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
	            String str = "";
	            try {
	                /**读取服务器返回过来的json字符串数据**/
	                str = EntityUtils.toString(response.getEntity());
	                /**把json字符串转换成json对象**/
	                jsonResult = net.sf.json.JSONObject.fromObject(str);
	            } catch (Exception e) {
	                logger.error("POST请求提交失败  {}", e);
	            }
	        } else {
	            logger.warn("POST请求服务器返回  「{}」", response.getStatusLine().getStatusCode());
	        }
	        return jsonResult;
	    }

	 
	/** 
	* @Title: HttpClient.java 
	* @author qierkang xyqierkang@163.com   
	* @date 2017年11月29日 下午12:34:34  
	* @param @param url
	* @param @param params
	* @param @param contextType
	* @param @param map
	* @param @return    设定文件 
	* @Description: TODO[ 发送json ]
	* @throws 
	*/
	public static String post(String url, String params,String contextType,Map<String, Object>map) {
		logger.info("httpclient JSONPOST 连接={},数据={}",url,params);
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(url);
			 if(map.get("msgType")!=null){
				 httpPost.setHeader("msgType", map.get("msgType").toString());
				 httpPost.setHeader("channel", map.get("channel").toString());
           }
			httpPost.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);
			StringEntity sEntity = new StringEntity(params, DEFAULT_CHARSET);
			sEntity.setContentType(contextType);
			sEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
			httpPost.setEntity(sEntity);
			CloseableHttpResponse response = httpClient.execute(httpPost);
			String result = getResultStr(url, response);
			if (result != null) return result;
		} catch (Exception e) {
			logger.error("HTTP请求异常",e);
		}
		return null;
	}
	
	public static String get(String fullUrl) {
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(fullUrl);
			httpGet.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);
			CloseableHttpResponse response = httpClient.execute(httpGet);
			String result = getResultStr(fullUrl, response);
			if (result != null) return result;
		} catch (Exception e) {
			logger.error("HTTP请求异常",e);
		}
		return null;
	}

	public static String getUrl(String url, String uri) {
		StringBuffer sb = new StringBuffer();
		sb.append(url);
		if (url.endsWith("/") && uri.startsWith("/")) {
			uri = uri.substring(1);
		} else if (!url.endsWith("/") && !uri.startsWith("/")) {
			sb.append("/");
		}
		sb.append(uri);
		return sb.toString();
	}
	
//	public static void main(String[] args) throws ClientProtocolException, IOException {
//	    	String url ="http://192.168.1.54:8080/medcfcApi/saveHospital";
//	        net.sf.json.JSONObject jsonResult = new net.sf.json.JSONObject();
//	        CloseableHttpClient httpClient = HttpClients.createDefault();
//		    MultipartEntity multipartMode = new MultipartEntity(HttpMultipartMode.STRICT,
//	                    "application/x-www-form-urlencoded", Charset.forName(DEFAULT_CHARSET));
//		    HttpPost method = new HttpPost(url);
//		    multipartMode.addPart("key", new StringBody("1111111111111234"));
//	            multipartMode.addPart("data", new StringBody("asdasd"));
//	            method.setEntity(multipartMode);
//	            startTime = System.currentTimeMillis(); 
//	            HttpResponse response = httpClient.execute(method);
//            jsonResult = handleResponse(response);
//	    System.out.println(jsonResult);
//	}
	
	 /** 
     * 设置上传文件时所附带的其他参数 
     *  
     * @param multipartEntityBuilder 
     * @param params 
     */  
    private void setUploadParams(MultipartEntityBuilder multipartEntityBuilder,  
            Map<String, String> params) {  
        if (params != null && params.size() > 0) {  
            Set<String> keys = params.keySet();  
            for (String key : keys) {  
                multipartEntityBuilder  
                        .addPart(key, new StringBody(params.get(key),  
                                ContentType.TEXT_PLAIN));  
            }  
        }  
    }  
    
    private String getRespString(HttpEntity entity) throws Exception {  
        if (entity == null) {  
            return null;  
        }  
        InputStream is = entity.getContent();  
        StringBuffer strBuf = new StringBuffer();  
        byte[] buffer = new byte[4096];  
        int r = 0;  
        while ((r = is.read(buffer)) > 0) {  
            strBuf.append(new String(buffer, 0, r, "UTF-8"));  
        }  
        return strBuf.toString();  
    }  
    
	public String uploadFileImpl(String serverUrl, String localFilePath,  
	                             String serverFieldName, Map<String, String> params)  
	                             throws Exception {  
	                         String respStr = null;  
	                         CloseableHttpClient httpclient = HttpClients.createDefault();  
	                         try {  
	                             HttpPost httppost = new HttpPost(serverUrl);  
	                             FileBody binFileBody = new FileBody(new File(localFilePath));  
	                   
	                             MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder  
	                                     .create();  
	                             // add the file params  
	                             multipartEntityBuilder.addPart(serverFieldName, binFileBody);  
	                             // 设置上传的其他参数  
	                             setUploadParams(multipartEntityBuilder, params);  
	                   
	                             HttpEntity reqEntity = multipartEntityBuilder.build();  
	                             httppost.setEntity(reqEntity);  
	                   
	                             CloseableHttpResponse response = httpclient.execute(httppost);  
	                             try {  
	                                 System.out.println(response.getStatusLine());  
	                                 HttpEntity resEntity = response.getEntity();  
	                                 respStr = getRespString(resEntity);  
	                                 EntityUtils.consume(resEntity);  
	                             } finally {  
	                                 response.close();  
	                             }  
	                         } finally {  
	                             httpclient.close();  
	                         }  
	                         System.out.println("resp=" + respStr);  
	                         return respStr;  
	                     }  
        	/** 
        	* @Title: HttpClient.java 
        	* @author qierkang xyqierkang@163.com   
        	* @date 2018年6月28日 上午3:11:37  
        	* @param @param url
        	* @param @param parms
        	* @param @param multipartFile
        	* @param @return    设定文件 
        	* @Description: TODO[ HTTP 文件上传 ]
        	* @throws 
        	*/
        	public static JSONObject postFileUrl(String url,Map<String, Object>parms,MultipartFile multipartFile) {
        	    CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        	    CloseableHttpResponse httpResponse = null;
        	    try {
                    HttpPost httpPost = new HttpPost(url);
                    MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
                    multipartEntityBuilder.addBinaryBody("image",multipartFile.getInputStream(),ContentType.MULTIPART_FORM_DATA, multipartFile.getName());
                    for (Entry<String,Object> map : parms.entrySet()) {
                        multipartEntityBuilder.addTextBody(map.getKey(), map.getValue().toString());
                    }
                    HttpEntity httpEntity = multipartEntityBuilder.build();
                    httpPost.setEntity(httpEntity);
                    logger.info("EK http post 文件上传 【地址[{}]参数[{}]文件[{}]】>方法名[{}]操作时间[{}]",url,parms,multipartFile,Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
                    httpResponse = httpClient.execute(httpPost);
                     JSONObject JSONObject = handleResponse(httpResponse);
                     System.out.println(JSONObject);
                     logger.info("EK http post 文件上传 返回[{}]操作时间[{}]",JSONObject,DateUtils.getDateTime());
                    return JSONObject;
                } catch (ClientProtocolException e) {
                    logger.error("EK ERROR ClientProtocolException [{}] http post 文件上传 【地址[{}]参数[{}]文件[{}]】>方法名[{}]操作时间[{}]",e.getMessage(),url,parms,multipartFile,Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
                } catch (IOException e) {
                    logger.error("EK ERROR IOException [{}] http post 文件上传 【地址[{}]参数[{}]文件[{}]】>方法名[{}]操作时间[{}]",e.getMessage(),url,parms,multipartFile,Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
                }finally {
                    try {
                        httpClient.close();
                        httpResponse.close();
                    } catch (IOException e) {
                        logger.error("EK ERROR 关闭IOException出错！ [{}] http post 文件上传 【地址[{}]参数[{}]文件[{}]】>方法名[{}]操作时间[{}]",e.getMessage(),url,parms,multipartFile,Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
                    }
                }
        	    return null;
        	}
}
