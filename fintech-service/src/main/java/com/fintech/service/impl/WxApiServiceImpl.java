package com.fintech.service.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.common.properties.AppConfig;
import com.fintech.dao.CompanyBaseinfoMapper;
import com.fintech.dao.LogOrderMapper;
import com.fintech.dao.OrderBaseinfoMapper;
import com.fintech.model.CompanyBaseinfo;
import com.fintech.model.LogOrder;
import com.fintech.model.OrderBaseinfo;
import com.fintech.model.domain.weixin.DataXmlPackageDo;
import com.fintech.model.domain.weixin.WeiXinButtonBean;
import com.fintech.model.domain.weixin.WeiXinButtonEnum;
import com.fintech.service.RedisService;
import com.fintech.service.WxApiService;
import com.fintech.util.DateUtils;
import com.fintech.util.HttpClient;
import com.fintech.util.HttpGetUtil;
import com.fintech.util.ResponseUtil;
import com.fintech.util.StringUtil;
import com.fintech.util.enumerator.ConstantInterface;
import com.fintech.util.sign.EncryptUtil;
import com.fintech.util.sign.ParamSignUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class WxApiServiceImpl implements WxApiService {
    private static final Logger logger = LoggerFactory.getLogger(WxApiServiceImpl.class);
    private static String WEIXIN_AUTH_TOKEN = "FINTECH";
    private static String webRootName = "https://www.duodingfen.com/fintech-app/";
    @Autowired
    private RedisService redisService;
    @Autowired
    private OrderBaseinfoMapper orderBaseinfoMapper;
    @Autowired
    private LogOrderMapper logOrderMapper;
    @Autowired
    private AppConfig appConfig;
    @Autowired
    private CompanyBaseinfoMapper companyBaseinfoMapper;

    @Override
    public Map<String, Object> wxOpenId(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String openid = "";
        boolean loginFlag = false;
        Map<String, Object> parms = new HashMap<>();
        response.setContentType(ConstantInterface.Enum.CONTENT_TYPE.CONTENT_TYPE_TEXTHTML.getValue());
        request.setCharacterEncoding(ConstantInterface.Enum.ENCODING.ENCODING_UTF8.getValue());
        response.setCharacterEncoding(ConstantInterface.Enum.ENCODING.ENCODING_UTF8.getValue());
        String code = request.getParameter("code");// 获取code
        Map<String, String> weixinMap = new HashMap<>();
        weixinMap.put("secret", appConfig.getWEIXIN_API_SECRET());
        weixinMap.put("appid", appConfig.getWEIXIN_API_APPID());
        weixinMap.put("grant_type", appConfig.getWEIXIN_API_GRANT_TYPE());
        weixinMap.put("code", code);
        String result = HttpGetUtil.httpRequestToString("https://api.weixin.qq.com/sns/oauth2/access_token", weixinMap);
        JSONObject jsonObject = JSONObject.fromObject(result);
//        if (jsonObject.isNullObject()) {
//            throw new Exception(ConstantInterface.AppValidateConfig.OrderValidate.ORDER_200005.toString());
//        }
        openid = jsonObject.get("openid").toString();
//         openid = "as65d4a65dw56ad48q6d4";
        parms.put("openId", openid);
        String token = redisService.get(openid);
        logger.info("EK 微信授权 openid[{}]token[{}]操作时间[{}]", openid, token==null?"第一次登陆 NULL":token, DateUtils.getDateTime());
        if (!StringUtil.isEmpty(token)) {
            loginFlag = true;
            logger.info("EK 微信授权 有 token[{}]操作时间[{}]", token, DateUtils.getDateTime());
            parms.put("token", token);
            String mobile = redisService.get(token);
            if (!StringUtil.isEmpty(mobile)) {
                logger.info("EK 微信授权 有 mobile[{}]操作时间[{}]", mobile, DateUtils.getDateTime());
                parms.put("mobile", mobile);
                Map<String, Object> mapOrder = new HashMap<>();
                mapOrder.put("custCellphone", mobile);
                OrderBaseinfo baseinfo = orderBaseinfoMapper.selectByPrimaryKeyWx(mapOrder);
                if (baseinfo != null) {
                    logger.info("EK 有录入定单 baseinfo[{}]操作时间[{}]", baseinfo, DateUtils.getDateTime());
                    LogOrder logOrder = logOrderMapper.selectByPrimaryKeyStatus(baseinfo.getOrderId());
                    parms.put("orderStatus", logOrder.getOrderStatus());
                    parms.put("orderOperation", logOrder.getOrderOperation());
                    parms.put("orderId", baseinfo.getOrderId());
                }
            }
        }
        parms.put("loginFlag", loginFlag);
        return parms;
    }
    
    
    @Override
    public Map<String, Object> wxOrderList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String openid = "";
        boolean loginFlag = false;
        Map<String, Object> parms = new HashMap<>();
        response.setContentType(ConstantInterface.Enum.CONTENT_TYPE.CONTENT_TYPE_TEXTHTML.getValue());
        request.setCharacterEncoding(ConstantInterface.Enum.ENCODING.ENCODING_UTF8.getValue());
        response.setCharacterEncoding(ConstantInterface.Enum.ENCODING.ENCODING_UTF8.getValue());
        String code = request.getParameter("code");// 获取code
        Map<String, String> weixinMap = new HashMap<>();
        weixinMap.put("secret", appConfig.getWEIXIN_API_SECRET());
        weixinMap.put("appid", appConfig.getWEIXIN_API_APPID());
        weixinMap.put("grant_type", appConfig.getWEIXIN_API_GRANT_TYPE());
        weixinMap.put("code", code);
        String result = HttpGetUtil.httpRequestToString("https://api.weixin.qq.com/sns/oauth2/access_token", weixinMap);
        JSONObject jsonObject = JSONObject.fromObject(result);
//        if (jsonObject.isNullObject()) {
//            throw new Exception(ConstantInterface.AppValidateConfig.OrderValidate.ORDER_200005.toString());
//        }
        openid = jsonObject.get("openid").toString();
//         openid = "as65d4a65dw56ad48q6d4";
        parms.put("openId", openid);
        String token = redisService.get(openid);
        logger.info("EK 微信授权 openid[{}]token[{}]操作时间[{}]", openid, token==null?"第一次登陆 NULL":token, DateUtils.getDateTime());
        if (!StringUtil.isEmpty(token)) {
            loginFlag = true;
            logger.info("EK 微信授权 有 token[{}]操作时间[{}]", token, DateUtils.getDateTime());
            parms.put("token", token);
            String mobile = redisService.get(token);
            if (!StringUtil.isEmpty(mobile)) {
                logger.info("EK 微信授权 有 mobile[{}]操作时间[{}]", mobile, DateUtils.getDateTime());
                parms.put("mobile", mobile);
                Map<String, Object> mapOrder = new HashMap<>();
                mapOrder.put("custCellphone", mobile);
                OrderBaseinfo baseinfo = orderBaseinfoMapper.selectByPrimaryKeyWx(mapOrder);
                if (baseinfo != null) {
                    logger.info("EK 有录入定单 baseinfo[{}]操作时间[{}]", baseinfo, DateUtils.getDateTime());
                    LogOrder logOrder = logOrderMapper.selectByPrimaryKeyStatus(baseinfo.getOrderId());
                    parms.put("orderStatus", logOrder.getOrderStatus());
                    parms.put("orderOperation", logOrder.getOrderOperation());
                    parms.put("orderId", baseinfo.getOrderId());
                }
            }
        }
        parms.put("loginFlag", loginFlag);
        return parms;
    }
    
    /* (非 Javadoc) 
    * <p>Title: wxSignature</p> 
    * <p>Description: </p> 
    * @return 
    * @see com.fintech.service.WxApiService#wxSignature() 
    */
    @Override
    public Map<String, Object> wxJSSignature() throws Exception{
        String jsapi_ticket=redisService.get("WEIXIN_TICKET");
        String noncestr = UUID.randomUUID().toString().replace("-", "").substring(0, 16);//随机字符串
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);//时间戳
        String str = "jsapi_ticket="+jsapi_ticket+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+appConfig.getWEIXIN_API_SIGNATURE_URL();
        String signature=ParamSignUtils.sign(str);
        logger.info("EK 微信授权 获取JS-SDK使用权限签名算法 结果：【appId[{}]timestamp[{}]nonceStr[{}]signature[{}]】 操作时间[{}]", appConfig.getWEIXIN_API_APPID(),timestamp,noncestr,signature, DateUtils.getDateTime());
        Map<String, Object>map=new HashMap<>();
        map.put("appId", appConfig.getWEIXIN_API_APPID());
        map.put("timestamp", timestamp);
        map.put("noncestr", noncestr);
        map.put("signature", signature);
        map.put("str", str);
        map.put("jsapi_ticket", jsapi_ticket);
        return map;
    }


    @Override
    public String qrCodeCompany(String companyId) {
        CompanyBaseinfo baseinfo= companyBaseinfoMapper.selectByPrimaryKeyInfo(companyId);
        if(baseinfo.getWeixinCodeUrl()!=null) {
            return baseinfo.getWeixinCodeUrl();
        }
        try {
           String accessToken= redisService.get("WEIXIN_ACCESS_TOKEN");
           String codeUrl= appConfig.getWEIXIN_API_QRCODE_URL().replace("{access_token}", accessToken);
           Map<String, Object>params=new HashMap<>();
           Map<String, Object>action_info=new HashMap<>();
           Map<String, Object>scene=new HashMap<>();
           params.put("action_name", "QR_LIMIT_SCENE");
           scene.put("scene_id", baseinfo.getCompanyId());
           scene.put("scene_str", baseinfo.getCompanyName());
           action_info.put("scene", scene);
           params.put("action_info", action_info);
           String result=HttpClient.jsonPost(codeUrl, params);
           JSONObject json = JSONObject.fromObject(result);
           if(json.containsKey("ticket")) {
               baseinfo.setWeixinCodeUrl(appConfig.getWEIXIN_API_SHOWQRCODE_URL().replace("{ticket}", json.getString("ticket")));
               companyBaseinfoMapper.updateByPrimaryKeySelective(baseinfo);
           }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return baseinfo.getWeixinCodeUrl();
    }
    
	/* (非 Javadoc) 
	* <p>Title: wxQrCode</p> 
	* <p>Description: </p> 
	* @param request
	* @param response
	* @throws Exception 
	* @see com.fintech.service.WxApiService#wxQrCode(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse) 
	*服务器配置 验证
	*/
	@Override
	public void wxQrCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//接收微验证消息
//        String signature = request.getParameter("signature");// 微信加密签名
//        String echo = request.getParameter("echostr");// 随机字符串
//        String timestamp = request.getParameter("timestamp");// 时间戳
//        String nonce = request.getParameter("nonce");// 随机数
//        logger.info("接受微信推送过来参数  signature[{}]echo[{}]timestamp[{}]nonce[{}]", signature, echo, timestamp, nonce);
//        String[] str = {WEIXIN_AUTH_TOKEN, timestamp, nonce};
//        Arrays.sort(str); // 字典序排序
//        String bigStr = str[0] + str[1] + str[2];
//        String digest = EncryptUtil.sha(bigStr).toLowerCase();
//        logger.info("微信授权加密后参数 [{}]", digest);
//        ResponseUtil.setOutputString(response, echo);
        //进行事件操作
        BufferedReader bis = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String line;
        String result = "";
        while ((line = bis.readLine()) != null) {
            result += line + "\r\n";
        }
        logger.info("EK>APP获取二维码微信参数：XML result [{}]方法名[{}]操作时间[{}]",result,Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
        //解析数据
        DataXmlPackageDo dataXmlPackageDO = dataXmlAnalytical(result);
        //添加微信 table bar 菜单
        buildWxTableBar();
        pushMessageByUser(dataXmlPackageDO, response);
	}
	
	  /* (非 Javadoc) 
	    * <p>Title: pushMessageByUser</p> 
	    * <p>Description: </p> 
	    * @param dataXmlPackageDO
	    * @param response 
	    * @see com.fintech.service.WxApiService#pushMessageByUser(com.fintech.model.domain.DataXmlPackageDo, javax.servlet.http.HttpServletResponse) 
	    *获取二维码商户 查询商户基本资料
	    */
	    @Override
	    public void pushMessageByUser(DataXmlPackageDo dataXmlPackageDO, HttpServletResponse response) {
	        try {
	            if (StringUtils.isNotBlank(dataXmlPackageDO.getEventKey())) {
	            	String eventKey = dataXmlPackageDO.getEventKey().replace("qrscene_", "");
	                CompanyBaseinfo baseinfo=companyBaseinfoMapper.selectByPrimaryKeyInfo(eventKey);
	                String pushData = pushImgAndTextMessage(dataXmlPackageDO,baseinfo);
	                ResponseUtil.setOutputString(response, pushData);
	            }
	        } catch (Exception e) {
	            logger.error("weChat template message push", e);
	        }
	    }
	    
	    /* (非 Javadoc) 
	    * <p>Title: pushImgAndTextMessage</p> 
	    * <p>Description: </p> 
	    * @param dataXmlPackageDO
	    * @param baseinfo
	    * @return
	    * @throws Exception 
	    * @see com.fintech.service.WxApiService#pushImgAndTextMessage(com.fintech.model.domain.DataXmlPackageDo, com.fintech.model.CompanyBaseinfo) 
	    *推送二维码模板消息
	    */
	    @Override
	    public String pushImgAndTextMessage(DataXmlPackageDo dataXmlPackageDO, CompanyBaseinfo baseinfo) throws Exception {
	        StringBuffer stringBuffer = new StringBuffer();
	        stringBuffer.append("<xml>");
	        stringBuffer.append("<ToUserName>" + dataXmlPackageDO.getToUserName() + "</ToUserName>");
	        stringBuffer.append("<FromUserName>" + dataXmlPackageDO.getFromUserName() + "</FromUserName>");
	        stringBuffer.append("<CreateTime>" + System.currentTimeMillis() + "</CreateTime>");
	        if (baseinfo == null || !"1".equals(baseinfo.getCompanyStatus())) {
	            String messageContent = "商户是禁用或还未开通，请联系运营人员！";
	            stringBuffer.append("<MsgType>text</MsgType>");
	            stringBuffer.append("<Content>" + messageContent + "</Content>");
	        } else {
	        	String url="https://www.duodingfen.com/fintech-app/app/orderbaseinfo/scanPiece?companyId="+baseinfo.getCompanyId()+"&openId="+dataXmlPackageDO.getFromUserName();
	        	String applyUrl = URLEncoder.encode(url, "utf-8");
	            stringBuffer.append("<MsgType>news</MsgType>");
	            stringBuffer.append("<ArticleCount>1</ArticleCount>");
	            stringBuffer.append("<Articles>");
	            stringBuffer.append("<item>");
	            stringBuffer.append("<Title>"+baseinfo.getCompanyName()+"</Title>");
	            stringBuffer.append("<Description>点击申请</Description>");
	            stringBuffer.append("<PicUrl>https://www.duodingfen.com/fintech-manage/img/icon/logo.png</PicUrl>");
	            stringBuffer.append("<Url>"+applyUrl+"</Url>");
	            stringBuffer.append("</item>");
//	            stringBuffer.append("<item>");
//	            stringBuffer.append("<Title>" + baseinfo.getCompanyName() + "</Title>");
//	            stringBuffer.append("<Description>点击申请</Description>");
//	            stringBuffer.append("<PicUrl>https://www.duodingfen.com/fintech-manage/img/icon/logo.png</PicUrl>");
//	            stringBuffer.append("<Url>" + applyUrl + "</Url>");
//	            stringBuffer.append("</item>");
	            stringBuffer.append("</Articles>");
	        }
	        stringBuffer.append("</xml>");
	        String sendDataContent = stringBuffer.toString();
	        logger.info("weChat message push {}", sendDataContent);
	        return sendDataContent;
	    }
	    
    /** 
    * @Title: WxApiServiceImpl.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年7月17日 上午2:54:23  
    * @param @param xmlStr
    * @param @return    设定文件 
    * @Description: TODO[ 数据解析 ]
    * @throws 
    */
    public DataXmlPackageDo dataXmlAnalytical(String xmlStr) {
    	DataXmlPackageDo xmlDataPackage = new DataXmlPackageDo();
        if (StringUtils.isNotBlank(xmlStr)) {
            try {
                SAXReader saxReader = new SAXReader();
                Document doc = saxReader.read(new ByteArrayInputStream(xmlStr.getBytes()));
                Element root = doc.getRootElement();
                @SuppressWarnings("unchecked")
				List<Element> childList = root.elements();
                for (Element ele : childList) {
                	if (ele.getName().equals("ToUserName")) {
                        xmlDataPackage.setToUserName(ele.getStringValue());
                    } else if (ele.getName().equals("FromUserName")) {
                        xmlDataPackage.setFromUserName(ele.getStringValue());
                    } else if (ele.getName().equals("CreateTime")) {
                        xmlDataPackage.setCreateTime(ele.getStringValue());
                    } else if (ele.getName().equals("MsgType")) {
                        xmlDataPackage.setMsgType(ele.getStringValue());
                    } else if (ele.getName().equals("Event")) {
                        xmlDataPackage.setEvent(ele.getStringValue());
                    } else if (ele.getName().equals("EventKey")) {
                        xmlDataPackage.setEventKey(ele.getStringValue());
                    } else if (ele.getName().equals("Ticket")) {
                        xmlDataPackage.setTicket(ele.getStringValue());
                    } else if (ele.getName().equals("Latitude")) {
                        xmlDataPackage.setLatitude(ele.getStringValue());
                    } else if (ele.getName().equals("Longitude")) {
                        xmlDataPackage.setLongitude(ele.getStringValue());
                    } else if (ele.getName().equals("Precision")) {
                        xmlDataPackage.setPrecision(ele.getStringValue());
                    }
                }
            } catch (Exception e) {
                logger.error("weChat target event analytical ", e);
            }
            logger.info("weChat target event analytical {}", xmlDataPackage.toString());
        }
        return xmlDataPackage;
    }


	/* (非 Javadoc) 
	* <p>Title: buildWxTableBar</p> 
	* <p>Description: </p> 
	* @throws Exception 
	* @see com.fintech.service.WxApiService#buildWxTableBar() 
	*/
	@Override
	public void buildWxTableBar() throws Exception {
		 List<WeiXinButtonBean> button = new ArrayList<>();
	        //我要进件
	        WeiXinButtonBean order = new WeiXinButtonBean();
	        order.setUrl(webRootName + WeiXinButtonEnum.MY_CHILDREN_ORDER_BUTTON.getButtonUrl());
	        order.setName(WeiXinButtonEnum.MY_CHILDREN_ORDER_BUTTON.getButtonName());
	        order.setType(WeiXinButtonEnum.MY_CHILDREN_ORDER_BUTTON.getButtonType());
	        button.add(order);
	        // 我的订单
	        WeiXinButtonBean buttonMyProblem = new WeiXinButtonBean();
	        buttonMyProblem.setType(WeiXinButtonEnum.MY_PROBLEM_BUTTON.getButtonType());
	        buttonMyProblem.setName(WeiXinButtonEnum.MY_PROBLEM_BUTTON.getButtonName());
	        buttonMyProblem.setUrl(webRootName + WeiXinButtonEnum.MY_PROBLEM_BUTTON.getButtonUrl());
	        button.add(buttonMyProblem);
	        //我要还款
	        WeiXinButtonBean buttonAppDownload = new WeiXinButtonBean();
	        buttonAppDownload.setType(WeiXinButtonEnum.MY_RETURN_BUTTON.getButtonType());
	        buttonAppDownload.setName(WeiXinButtonEnum.MY_RETURN_BUTTON.getButtonName());
	        buttonAppDownload.setUrl(webRootName + WeiXinButtonEnum.MY_RETURN_BUTTON.getButtonUrl());
	        button.add(buttonAppDownload);
	        //请求链接
	        String requestButtonTableBarUrl = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + redisService.get("WEIXIN_ACCESS_TOKEN");
	        //table bar数据
	        JSONArray requestButtonTableBarArray = JSONArray.fromObject(button);
	        JSONObject requestButtonTableBar = new JSONObject();
	        requestButtonTableBar.put("button", requestButtonTableBarArray);
	        JSONObject response = HttpClient.httpPost(requestButtonTableBarUrl, requestButtonTableBar,false);
	        logger.info("微信执行 TABLE BAR 地址 {}", requestButtonTableBarUrl);
	        logger.info("微信执行 TABLE BAR 数据 {}", String.valueOf(requestButtonTableBar));
	        logger.info("微信执行 TABLE BAR 返回 {}", response.toString());
	}
}
