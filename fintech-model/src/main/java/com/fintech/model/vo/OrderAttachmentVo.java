package com.fintech.model.vo;

import java.util.Date;

public class OrderAttachmentVo extends BaseVO {
    private Integer id;

    private String orderId;

    private String atthType;

    private String atthPath;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAtthType() {
        return atthType;
    }

    public void setAtthType(String atthType) {
        this.atthType = atthType;
    }

    public String getAtthPath() {
        return atthPath;
    }

    public void setAtthPath(String atthPath) {
        this.atthPath = atthPath;
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

    @Override
    public String toString() {
        return "OrderAttachmentVo [id=" + id + ", orderId=" + orderId + ", atthType=" + atthType + ", atthPath="
                + atthPath + ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
    }
}