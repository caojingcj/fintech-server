package com.fintech.model;

import java.util.Date;

public class LogMoxieinfo {
    private Long id;

    private String moxieMobile;

    private String moxieIdcard;

    private String moxieName;

    private String moxieType;

    private String orderId;

    private String moxieTaskId;

    private Integer moxieStatus;

    private String moxieOnlineUrl;

    private Date reportTime;

    private Date createTime;

    private Date updateTime;

    private String moxieContent;

    public String getMoxieContent() {
        return moxieContent;
    }

    public void setMoxieContent(String moxieContent) {
        this.moxieContent = moxieContent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMoxieMobile() {
        return moxieMobile;
    }

    public void setMoxieMobile(String moxieMobile) {
        this.moxieMobile = moxieMobile;
    }

    public String getMoxieIdcard() {
        return moxieIdcard;
    }

    public void setMoxieIdcard(String moxieIdcard) {
        this.moxieIdcard = moxieIdcard;
    }

    public String getMoxieName() {
        return moxieName;
    }

    public void setMoxieName(String moxieName) {
        this.moxieName = moxieName;
    }

    public String getMoxieType() {
        return moxieType;
    }

    public void setMoxieType(String moxieType) {
        this.moxieType = moxieType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMoxieTaskId() {
        return moxieTaskId;
    }

    public void setMoxieTaskId(String moxieTaskId) {
        this.moxieTaskId = moxieTaskId;
    }

    public Integer getMoxieStatus() {
        return moxieStatus;
    }

    public void setMoxieStatus(Integer moxieStatus) {
        this.moxieStatus = moxieStatus;
    }

    public String getMoxieOnlineUrl() {
        return moxieOnlineUrl;
    }

    public void setMoxieOnlineUrl(String moxieOnlineUrl) {
        this.moxieOnlineUrl = moxieOnlineUrl;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
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