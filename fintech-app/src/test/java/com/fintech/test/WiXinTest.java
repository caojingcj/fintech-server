package com.fintech.test;

import java.util.UUID;

import org.junit.Test;

import com.fintech.util.HttpClient;
import com.fintech.util.sign.ParamSignUtils;

import net.sf.json.JSONObject;


public class WiXinTest {

    /** 
    * @Title: WiXinTest.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年7月1日 上午12:01:23  
    * @param     设定文件 
    * @Description: TODO[ JS 票据验签 ]
    * @throws 
    */
    @Test
    public void wxSignatureTest() {
        String WEIXIN_API_ACCESS_TOKEN = HttpClient.get("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx4e291d39c10f3c63&secret=1ac45a12a035d3a279b69d7f0e58aaa5");
        JSONObject tokenJson= JSONObject.fromObject(WEIXIN_API_ACCESS_TOKEN);
        String token=tokenJson.get("access_token").toString();
        String WEIXIN_API_JSAPI = HttpClient.get("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+token+"&type=jsapi");
        JSONObject ticket= JSONObject.fromObject(WEIXIN_API_JSAPI);
        String jsapi_ticket=ticket.get("ticket").toString();
        String noncestr = UUID.randomUUID().toString().replace("-", "").substring(0, 16);//随机字符串
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);//时间戳
        String url="https://www.duodingfen.com";
        String str = "jsapi_ticket="+jsapi_ticket+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+url;
        System.out.println(str);
        String signature=ParamSignUtils.sign(str);
        System.out.println("appId：wx4e291d39c10f3c63\ntimestamp："+timestamp+"\nnonceStr："+noncestr+"\nsignature："+signature);
    }
}
