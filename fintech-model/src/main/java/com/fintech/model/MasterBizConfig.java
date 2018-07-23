package com.fintech.model;

import java.util.Date;

public class MasterBizConfig {
    private String bizConfigCode;

    private String bizConfigName;

    private String bizConfigValue;

    private Date createTime;

    private Date updatetime;

    public String getBizConfigCode() {
        return bizConfigCode;
    }

    public void setBizConfigCode(String bizConfigCode) {
        this.bizConfigCode = bizConfigCode;
    }

    public String getBizConfigName() {
        return bizConfigName;
    }

    public void setBizConfigName(String bizConfigName) {
        this.bizConfigName = bizConfigName;
    }

    public String getBizConfigValue() {
        return bizConfigValue;
    }

    public void setBizConfigValue(String bizConfigValue) {
        this.bizConfigValue = bizConfigValue;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}