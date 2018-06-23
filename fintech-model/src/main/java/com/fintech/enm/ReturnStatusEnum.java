package com.fintech.enm;

public enum ReturnStatusEnum {

    未还款("0"),
    已还款("1"),
    已提前结清("9"),
    已取消("8");
    
    private String value;

    public String getValue() {
        return value;
    }

    private ReturnStatusEnum(String val) {
        this.value = val;
    }
    
}
