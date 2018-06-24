package com.fintech.enm;

/**
 * 性别
 * @author caojing
 */
public enum GenderEnum {

    男("1"),
    女("2");
    
    private String value;

    public String getValue() {
        return value;
    }

    private GenderEnum(String val) {
        this.value = val;
    }
    
}
