package com.fintech.enm;

/**
 * 身份证认证状态
 * @author caojing
 */
public enum IdentityStatusEnum {

    未认证("0"),
    已认证("1"),
    已过期("2");
    
    private String value;

    public String getValue() {
        return value;
    }

    private IdentityStatusEnum(String val) {
        this.value = val;
    }
    
}
