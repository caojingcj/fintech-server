package com.fintech.common.moxie;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fintech.common.properties.AppConfig;

import net.sf.json.JSONObject;


/**   
* @Title: MoxieUtil.java 
* @Package com.fintech.common.moxie 
* @author qierkang xyqierkang@163.com   
* @date 2018年6月23日 下午8:14:44  
* @Description: TODO[ 用一句话描述该文件做什么 ]
*/
@Component
public class MoxieUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(MoxieUtil.class);
    /** 
    * @Title: MoxieUtil.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年6月30日 上午1:58:34  
    * @param @param url
    * @param @param jsonParam
    * @param @return
    * @param @throws Exception    设定文件 
    * @Description: TODO[ 魔杖获取token  post ]
    * @throws 
    */
    public static String doPostWithApikey(String url, JSONObject jsonParam,String token) throws Exception {
        LOGGER.debug("[moxie] doPostWithApikey() start! \n\turl ={} \n\tjsonParam={}.", url, jsonParam);
        if (StringUtils.isEmpty(jsonParam) || StringUtils.isEmpty(url)) {
            LOGGER.error("[moxie] doPostWithApikey() parameter url or jsonParam is null !");
            throw new Exception("MoxieUtil.doPostWithApikey() => parameter url or jsonParam is null !");
        }
        CloseableHttpClient httpClient;
        HttpPost httpPost;
        StringEntity stringEntity;
        HttpResponse response;
        HttpEntity entity;
        String result = null;
        try {
            //创建默认的httpClient实例
            httpClient = HttpClients.createDefault();
            //创建httpPost
            httpPost = new HttpPost(url);
            httpPost.setHeader("Authorization", token);
            httpPost.setHeader("Content-Type", "application/json");
            stringEntity = new StringEntity(jsonParam.toString(), "utf-8");
            stringEntity.setContentEncoding("UTF-8");
            httpPost.setEntity(stringEntity);

            response = httpClient.execute(httpPost);
            entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity);
                LOGGER.debug("=> End doPostWithApikey() .");
            } else {
                LOGGER.error("=> doPostWithApikey(),response.getEntity() is null !");
            }
        } catch (Exception e) {
            LOGGER.error("=> doPostWithApikey() throw Exception ! ", e);
        }
        return result;
    }

    public static String doGetWithApikey(String url,String token) throws Exception {
        LOGGER.debug("[moxie] doGetWithApikey() start! \n\turl = {}", url);
        if (StringUtils.isEmpty(url)) {
            LOGGER.error("[moxie] doGetWithApikey() parameter url is null !");
            throw new Exception("MoxieUtil.doGetWithApikey() => parameter url is null !");
        }
        CloseableHttpClient httpClient;
        HttpGet httpGet;
        HttpResponse response;
        HttpEntity entity;
        String result = null;
        try {
            //创建默认的httpClient实例
            httpClient = HttpClients.createDefault();
            //创建httpGet
            httpGet = new HttpGet(url);
            httpGet.setHeader("Authorization", token);
            response = httpClient.execute(httpGet);
            entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity);
                LOGGER.debug("[moxie] doGetWithApikey() finish!");
            } else {
                LOGGER.error("[moxie] doGetWithApikey() response.getEntity() is null !");
            }
        } catch (Exception e) {
            LOGGER.error("[moxie] doGetWithApikey() exception! ", e);
        }
        return result;
    }

    public static String doGetWithToken(String url,String token) throws Exception {
        LOGGER.debug("[moxie] doGetWithToken() start! url = {}", url);
        if (StringUtils.isEmpty(url)) {
            LOGGER.error("[moxie] doGetWithToken() parameter url is null !");
            throw new Exception("MoxieUtil.doGetWithToken() => parameter url is null !");
        }
        CloseableHttpClient httpClient;
        HttpGet httpGet;
        HttpResponse response;
        HttpEntity entity;
        String result = null;
        try {
            //创建默认的httpClient实例
            httpClient = HttpClients.createDefault();
            //创建httpGet
            httpGet = new HttpGet(url);
            httpGet.setHeader("Authorization", token);
//            httpGet.setHeader("Authorization", "token db401c3ba5694a7b91205f8749d993f3");
            response = httpClient.execute(httpGet);
            entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity);
                LOGGER.debug("[moxie] doGetWithToken() finish!");
            } else {
                LOGGER.error("[moxie] doGetWithToken() response.getEntity() is null !");
            }
        } catch (Exception e) {
            LOGGER.error("[moxie] doGetWithToken() exception! ", e);
        }
        return result;
    }

    public static void returnMoxieSuccStatus(HttpServletResponse response) {
        PrintWriter printWriter = null;
        try {
            response.setStatus(201);
            printWriter = response.getWriter();
            printWriter.write("default eventtype");
        } catch (IOException e) {
            LOGGER.error("[moxie] return moxie status exception!", e);
        } finally {
            printWriter.close();
        }
    }
    
    public static void main(String[] args) {
        String url="https://api.51datakey.com/carrier/v3/mobiles/{mobile}/mxdata-ex?task_id={task_id}";
        try {
            String result = MoxieUtil.doGetWithToken(url
                    .replace("{task_id}", "6f91bbd0-76da-11e8-b29a-00163e13fa10")
                    .replace("{mobile}", "13813948485"),"6f91bbd0-76da-11e8-b29a-00163e13fa10");
            System.out.println(result);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
