package com.fintech.enm;

/**
 * 还款渠道
 * @author caojing
 *
 */
public enum ReturnChannelEnum {

    线下("9");

    private String value;

    public String getValue() {
        return value;
    }

    private ReturnChannelEnum(String val) {
        this.value = val;
    }
    
}
