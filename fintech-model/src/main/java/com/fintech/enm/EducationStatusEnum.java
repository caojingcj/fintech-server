package com.fintech.enm;

/**
 * 学历
 * @author caojing
 */
public enum EducationStatusEnum {

    博士("1"),
    硕士("2"),
    本科("3"),
    专科("4"),
    高中("5"),
    中专技校职校("6"),
    初中("7"),
    小学("8");
    
    private String value;

    public String getValue() {
        return value;
    }

    private EducationStatusEnum(String val) {
        this.value = val;
    }
    
}
