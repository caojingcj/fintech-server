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
    @Value("${QYS_SERVER_URL}")
    private String QYS_SERVER_URL;
    /**
    * @Fields QYS_ACCESS_KEY : TODO[ 访问密钥 ]
    */
    @Value("${QYS_ACCESS_KEY}")
    private String QYS_ACCESS_KEY;
    /**
    * @Fields QYES_ACCESS_SECRET : TODO[ 访问密钥 ]
    */
    @Value("${QYES_ACCESS_SECRET}")
    private String QYES_ACCESS_SECRET;
    @Value("${QYES_CA_SEALID}")
    private String QYES_CA_SEALID;
    @Value("${QYES_CA_DOCID}")
    private String QYES_CA_DOCID;
    @Value("${QYES_CA_STAMP}")
    private String QYES_CA_STAMP;
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
    @Value("${ENDPOINT}")
    private String ENDPOINT;
    @Value("${ACCESS_KEY_ID}")
    private String ACCESS_KEY_ID;
    @Value("${ACCESS_KEY_SECRET}")
    private String ACCESS_KEY_SECRET;
    @Value("${BUCKET_NAME}")
    private String BUCKET_NAME;
    @Value("${OSS_ORDER_ATTACMENT_PATH}")
    private String OSS_ORDER_ATTACMENT_PATH;

    
    public String getQYES_CA_SEALID() {
        return QYES_CA_SEALID;
    }

    public void setQYES_CA_SEALID(String qYES_CA_SEALID) {
        QYES_CA_SEALID = qYES_CA_SEALID;
    }

    public String getQYES_CA_DOCID() {
        return QYES_CA_DOCID;
    }

    public void setQYES_CA_DOCID(String qYES_CA_DOCID) {
        QYES_CA_DOCID = qYES_CA_DOCID;
    }

    public String getQYES_CA_STAMP() {
        return QYES_CA_STAMP;
    }

    public void setQYES_CA_STAMP(String qYES_CA_STAMP) {
        QYES_CA_STAMP = qYES_CA_STAMP;
    }

    public String ACCESS_KEY_SECRET() {
        return ENDPOINT;
    }

    public void setENDPOINT(String eNDPOINT) {
        ENDPOINT = eNDPOINT;
    }

    public String getENDPOINT() {
        return ENDPOINT;
    }

    public String getACCESS_KEY_ID() {
        return ACCESS_KEY_ID;
    }

    public void setACCESS_KEY_ID(String aCCESS_KEY_ID) {
        ACCESS_KEY_ID = aCCESS_KEY_ID;
    }

    public String getACCESS_KEY_SECRET() {
        return ACCESS_KEY_SECRET;
    }

    public void setACCESS_KEY_SECRET(String aCCESS_KEY_SECRET) {
        ACCESS_KEY_SECRET = aCCESS_KEY_SECRET;
    }

    public String getBUCKET_NAME() {
        return BUCKET_NAME;
    }

    public void setBUCKET_NAME(String bUCKET_NAME) {
        BUCKET_NAME = bUCKET_NAME;
    }

    public String getOSS_ORDER_ATTACMENT_PATH() {
        return OSS_ORDER_ATTACMENT_PATH;
    }

    public void setOSS_ORDER_ATTACMENT_PATH(String oSS_ORDER_ATTACMENT_PATH) {
        OSS_ORDER_ATTACMENT_PATH = oSS_ORDER_ATTACMENT_PATH;
    }

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

    public String getQYS_SERVER_URL() {
        return QYS_SERVER_URL;
    }

    public void setQYS_SERVER_URL(String qYS_SERVER_URL) {
        QYS_SERVER_URL = qYS_SERVER_URL;
    }

    public String getQYS_ACCESS_KEY() {
        return QYS_ACCESS_KEY;
    }

    public void setQYS_ACCESS_KEY(String qYS_ACCESS_KEY) {
        QYS_ACCESS_KEY = qYS_ACCESS_KEY;
    }

    public String getQYES_ACCESS_SECRET() {
        return QYES_ACCESS_SECRET;
    }

    public void setQYES_ACCESS_SECRET(String qYES_ACCESS_SECRET) {
        QYES_ACCESS_SECRET = qYES_ACCESS_SECRET;
    }

}
