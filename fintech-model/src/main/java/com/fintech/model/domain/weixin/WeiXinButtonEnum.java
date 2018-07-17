package com.fintech.model.domain.weixin;

public enum WeiXinButtonEnum {

    MY_CHILDREN_ORDER_BUTTON(WeiXinButtonBean.TYPE_CLICK_HTML, "我要进件","/app/weixin/wxCode"),
    MY_PROBLEM_BUTTON(WeiXinButtonBean.TYPE_CLICK_HTML, "我的订单", "/app/weixin/wxOrderCode"),
    MY_RETURN_BUTTON(WeiXinButtonBean.TYPE_CLICK_HTML, "我要还款", "/app/weixin/wxReturnCode");

    private String buttonType;
    private String buttonName;
    private String buttonUrl;

    WeiXinButtonEnum(String buttonType, String buttonName, String buttonUrl) {
        this.buttonType = buttonType;
        this.buttonName = buttonName;
        this.buttonUrl = buttonUrl;
    }

    public String getButtonType() {
        return buttonType;
    }

    public void setButtonType(String buttonType) {
        this.buttonType = buttonType;
    }

    public String getButtonName() {
        return buttonName;
    }

    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getButtonUrl() {
        return buttonUrl;
    }

    public void setButtonUrl(String buttonUrl) {
        this.buttonUrl = buttonUrl;
    }
}
