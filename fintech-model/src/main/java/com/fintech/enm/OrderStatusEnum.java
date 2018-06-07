package com.fintech.enm;

/**
 * 订单状态
 * @author caojing
 */
public enum OrderStatusEnum {

    录入中("00"),
    待审批("01"),
    审批中("02"),
    审批拒绝("03"),
    待用户签署("04"),
    分期还款中("05"),
    已取消("11"),
    已退款("12"),
    已提前结清("13"),
    已结清("99"),
    取消确认中("09");

    private String value;

    public String getValue() {
        return value;
    }

    private OrderStatusEnum(String val) {
        this.value = val;
    }

}
