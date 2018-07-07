package com.fintech.model;

import java.util.Date;

public class OrderAttachment {
    private Integer id;

    private String orderId;

    private String atthType;

    private String atthPath;

    private Date createTime;

    private Date updateTime;

	public OrderAttachment(String orderId, String atthType, String atthPath) {
		this.orderId = orderId;
		this.atthType = atthType;
		this.atthPath = atthPath;
	}

	public OrderAttachment() {
	}

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
}