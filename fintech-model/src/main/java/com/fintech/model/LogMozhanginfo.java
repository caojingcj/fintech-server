package com.fintech.model;

import java.util.Date;

public class LogMozhanginfo {
    private Long id;

    private String mozhangMobile;

    private String mozhangIdcard;

    private String mozhangName;

    private String mozhangContent;

    private String orderId;

    private String mozhangTaskId;

    private Date createTime;

    private Date updateTime;

    private Date reportTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMozhangMobile() {
        return mozhangMobile;
    }

    public void setMozhangMobile(String mozhangMobile) {
        this.mozhangMobile = mozhangMobile;
    }

    public String getMozhangIdcard() {
        return mozhangIdcard;
    }

    public void setMozhangIdcard(String mozhangIdcard) {
        this.mozhangIdcard = mozhangIdcard;
    }

    public String getMozhangName() {
        return mozhangName;
    }

    public void setMozhangName(String mozhangName) {
        this.mozhangName = mozhangName;
    }

    public String getMozhangContent() {
        return mozhangContent;
    }

    public void setMozhangContent(String mozhangContent) {
        this.mozhangContent = mozhangContent;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMozhangTaskId() {
        return mozhangTaskId;
    }

    public void setMozhangTaskId(String mozhangTaskId) {
        this.mozhangTaskId = mozhangTaskId;
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

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }
}