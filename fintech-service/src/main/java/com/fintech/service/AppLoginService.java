package com.fintech.service;

public interface AppLoginService {
    String appLoginVerification(String mobile,String openId,  String code) throws Exception;

    void appLogin(String mobile) throws Exception;
}
