package com.fintech.model;

import java.util.Date;

public class LogOcridcard {
    private Long id;

    private String ocrMobile;

    private String ocrName;

    private String ocrRequestId;

    private String ocrLegality;

    private String ocrSide;

    private String ocrIdCard;

    private String ocrContent;

    private String orderId;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOcrMobile() {
        return ocrMobile;
    }

    public void setOcrMobile(String ocrMobile) {
        this.ocrMobile = ocrMobile;
    }

    public String getOcrName() {
        return ocrName;
    }

    public void setOcrName(String ocrName) {
        this.ocrName = ocrName;
    }

    public String getOcrRequestId() {
        return ocrRequestId;
    }

    public void setOcrRequestId(String ocrRequestId) {
        this.ocrRequestId = ocrRequestId;
    }

    public String getOcrLegality() {
        return ocrLegality;
    }

    public void setOcrLegality(String ocrLegality) {
        this.ocrLegality = ocrLegality;
    }

    public String getOcrSide() {
        return ocrSide;
    }

    public void setOcrSide(String ocrSide) {
        this.ocrSide = ocrSide;
    }

    public String getOcrIdCard() {
        return ocrIdCard;
    }

    public void setOcrIdCard(String ocrIdCard) {
        this.ocrIdCard = ocrIdCard;
    }

    public String getOcrContent() {
        return ocrContent;
    }

    public void setOcrContent(String ocrContent) {
        this.ocrContent = ocrContent;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}