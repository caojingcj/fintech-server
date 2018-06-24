package com.fintech.common.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Title: Config.java
 * @Package com.medcfc.biz.properties
 * @author qierkang xyqierkang@163.com
 * @date 2018年5月19日 下午6:01:07
 * @Description: TODO[ 用一句话描述该文件做什么 ]
 */

@Component("appconfig")
public class AppConfig {
    /**
     * @Fields QYS_SERVER_URL : TODO[ 契约锁API地址 ]
     */
    // @Value("${QYS_SERVER_URL}")
    // private String QYS_SERVER_URL;
    // /**
    // * @Fields QYS_ACCESS_KEY : TODO[ 访问密钥 ]
    // */
    // @Value("${QYS_ACCESS_KEY}")
    // private String QYS_ACCESS_KEY;
    // /**
    // * @Fields QYES_ACCESS_SECRET : TODO[ 访问密钥 ]
    // */
    // @Value("${QYES_ACCESS_SECRET}")
    // private String QYES_ACCESS_SECRET;
    /** 
    * @Fields MOXIE_APIKEY : TODO[ 魔蝎APIKEY ] 
    */
    @Value("${MOXIE_APIKEY}")
    private String MOXIE_APIKEY;
    /** 
    * @Fields MOXIE_TOKEN : TODO[ 魔蝎 TOKEN] 
    */
    @Value("${MOXIE_TOKEN}")
    private String MOXIE_TOKEN;
    /** 
    * @Fields MOXIE_BACKURL : TODO[ 用一句话描述这个变量表示什么 ] 
    */
    @Value("${MOXIE_BACKURL}")
    private String MOXIE_BACKURL;

    /** 
    * @Fields MOXIE_TYPE_MXDATAEX : TODO[ 魔蝎-获取运营商报告全量 ] 
    */
    @Value("${MOXIE_TYPE_MXDATAEX}")
    private String MOXIE_TYPE_MXDATAEX;
    /** 
    * @Fields MOXIE_TYPE_BILL : TODO[ 魔蝎-获取账号基本记录] 
    */
    @Value("${MOXIE_TYPE_BILL}")
    private String MOXIE_TYPE_BILL;
    /** 
    * @Fields MOXIE_TYPE_BASIC : TODO[ 魔蝎-获取账号账单记录 ] 
    */
    @Value("${MOXIE_TYPE_BASIC}")
    private String MOXIE_TYPE_BASIC;
    /** 
    * @Fields MOXIE_TYPE_CALL : TODO[ 魔蝎-获取账号通话详单 ] 
    */
    @Value("${MOXIE_TYPE_CALL}")
    private String MOXIE_TYPE_CALL;
    /** 
    * @Fields MOXIE_TYPE_SMS : TODO[ 魔蝎-获取账号短信详单 ] 
    */
    @Value("${MOXIE_TYPE_SMS}")
    private String MOXIE_TYPE_SMS;

    /** 
    * @Fields ALI_SMS_ACCESSKEYID : TODO[ 用一句话描述这个变量表示什么 ] 
    */
    @Value("${ALI_SMS_ACCESSKEYID}")
    private String ALI_SMS_ACCESSKEYID;

    /** 
    * @Fields ALI_SMS_ACCESSKEYSECRET : TODO[ 用一句话描述这个变量表示什么 ] 
    */
    @Value("${ALI_SMS_ACCESSKEYSECRET}")
    private String ALI_SMS_ACCESSKEYSECRET;

    /** 
    * @Fields ALI_SMS_TEMPCODE : TODO[ 用一句话描述这个变量表示什么 ] 
    */
    @Value("${ALI_SMS_TEMPCODE}")
    private String ALI_SMS_TEMPCODE;
    @Value("${ALI_SMS_TEMPCONTENT}")
    private String ALI_SMS_TEMPCONTENT;

    public String getALI_SMS_TEMPCONTENT() {
        return ALI_SMS_TEMPCONTENT;
    }

    public void setALI_SMS_TEMPCONTENT(String aLI_SMS_TEMPCONTENT) {
        ALI_SMS_TEMPCONTENT = aLI_SMS_TEMPCONTENT;
    }

    public String getMOXIE_TYPE_BASIC() {
        return MOXIE_TYPE_BASIC;
    }

    public String getMOXIE_TYPE_MXDATAEX() {
        return MOXIE_TYPE_MXDATAEX;
    }

    public void setMOXIE_TYPE_MXDATAEX(String mOXIE_TYPE_MXDATAEX) {
        MOXIE_TYPE_MXDATAEX = mOXIE_TYPE_MXDATAEX;
    }

    public String getMOXIE_TYPE_BILL() {
        return MOXIE_TYPE_BILL;
    }

    public void setMOXIE_TYPE_BILL(String mOXIE_TYPE_BILL) {
        MOXIE_TYPE_BILL = mOXIE_TYPE_BILL;
    }

    public void setMOXIE_TYPE_BASIC(String mOXIE_TYPE_BASIC) {
        MOXIE_TYPE_BASIC = mOXIE_TYPE_BASIC;
    }

    public String getMOXIE_TYPE_CALL() {
        return MOXIE_TYPE_CALL;
    }

    public void setMOXIE_TYPE_CALL(String mOXIE_TYPE_CALL) {
        MOXIE_TYPE_CALL = mOXIE_TYPE_CALL;
    }

    public String getMOXIE_TYPE_SMS() {
        return MOXIE_TYPE_SMS;
    }

    public void setMOXIE_TYPE_SMS(String mOXIE_TYPE_SMS) {
        MOXIE_TYPE_SMS = mOXIE_TYPE_SMS;
    }

    public String getMOXIE_APIKEY() {
        return MOXIE_APIKEY;
    }

    public void setMOXIE_APIKEY(String mOXIE_APIKEY) {
        MOXIE_APIKEY = mOXIE_APIKEY;
    }

    public String getMOXIE_TOKEN() {
        return MOXIE_TOKEN;
    }

    public void setMOXIE_TOKEN(String mOXIE_TOKEN) {
        MOXIE_TOKEN = mOXIE_TOKEN;
    }

    public String getMOXIE_BACKURL() {
        return MOXIE_BACKURL;
    }

    public void setMOXIE_BACKURL(String mOXIE_BACKURL) {
        MOXIE_BACKURL = mOXIE_BACKURL;
    }

    public String getALI_SMS_ACCESSKEYID() {
        return ALI_SMS_ACCESSKEYID;
    }

    public void setALI_SMS_ACCESSKEYID(String aLI_SMS_ACCESSKEYID) {
        ALI_SMS_ACCESSKEYID = aLI_SMS_ACCESSKEYID;
    }

    public String getALI_SMS_ACCESSKEYSECRET() {
        return ALI_SMS_ACCESSKEYSECRET;
    }

    public void setALI_SMS_ACCESSKEYSECRET(String aLI_SMS_ACCESSKEYSECRET) {
        ALI_SMS_ACCESSKEYSECRET = aLI_SMS_ACCESSKEYSECRET;
    }

    public String getALI_SMS_TEMPCODE() {
        return ALI_SMS_TEMPCODE;
    }

    public void setALI_SMS_TEMPCODE(String aLI_SMS_TEMPCODE) {
        ALI_SMS_TEMPCODE = aLI_SMS_TEMPCODE;
    }

}
