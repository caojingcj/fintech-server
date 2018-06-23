package com.fintech.enm;

/**
 * 信审结果
 * @author caojing
 */
public enum CreditVettingResultEnum {

    通过("1"),
    拒绝("0"),
    人工("2");
    
    private String value;

    public String getValue() {
        return value;
    }

    private CreditVettingResultEnum(String val) {
        this.value = val;
    }
    
}
