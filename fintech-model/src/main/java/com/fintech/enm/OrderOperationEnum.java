package com.fintech.enm;

/**
 * 订单操作类型
 * @author caojing
 */
public enum OrderOperationEnum {

    扫码("00"),
    项目信息填写("01"),
    身份信息认证("02"),
    运营商认证("03"),
    个人信息填写("04"),
    附件上传("05"),
    签署合同("06"),
    审批("91"),
    取消("11"),
    提前结清("13"),
    结清("99"),
    取消确认("09"),
    取消驳回("81"),
    担保("70"),
    担保确认("71"),
    担保驳回("72");
    
    private String value;

    public String getValue() {
        return value;
    }

    private OrderOperationEnum(String val) {
        this.value = val;
    }
    
}
