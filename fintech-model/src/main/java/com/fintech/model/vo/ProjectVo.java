package com.fintech.model.vo;

public class ProjectVo extends BaseVO {
    private String orderId;
    private String companyId;
    private String companyName;
    private String itemCode;
    private String itemName;
    private Integer totalPeriod;
    private String orderAmount;
    private Integer companyChannelId;
    private String companyChannelName;
    private String companyChannelPhone;

    public String getCompanyChannelPhone() {
        return companyChannelPhone;
    }

    public void setCompanyChannelPhone(String companyChannelPhone) {
        this.companyChannelPhone = companyChannelPhone;
    }

    public Integer getCompanyChannelId() {
        return companyChannelId;
    }

    public void setCompanyChannelId(Integer companyChannelId) {
        this.companyChannelId = companyChannelId;
    }

    public String getCompanyChannelName() {
        return companyChannelName;
    }

    public void setCompanyChannelName(String companyChannelName) {
        this.companyChannelName = companyChannelName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getTotalPeriod() {
        return totalPeriod;
    }

    public void setTotalPeriod(Integer totalPeriod) {
        this.totalPeriod = totalPeriod;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    @Override
    public String toString() {
        return "ProjectVo [orderId=" + orderId + ", companyId=" + companyId + ", companyName=" + companyName
                + ", itemCode=" + itemCode + ", itemName=" + itemName + ", totalPeriod=" + totalPeriod
                + ", orderAmount=" + orderAmount + "]";
    }

}
