package com.fintech.model;

import java.util.Date;

public class LogOrder {
    private Integer id;

    private String orderId;

    private String orderOperation;

    private String orderStatus;
    
    private String orderNote;

    private Date createTime;

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

    public String getOrderOperation() {
        return orderOperation;
    }

    public void setOrderOperation(String orderOperation) {
        this.orderOperation = orderOperation;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderNote() {
        return orderNote;
    }

    public void setOrderNote(String orderNote) {
        this.orderNote = orderNote;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public LogOrder() {
    }

    public LogOrder(String orderId, String orderOperation, String orderStatus, String orderNote) {
        super();
        this.orderId = orderId;
        this.orderOperation = orderOperation;
        this.orderStatus = orderStatus;
        this.orderNote = orderNote;
    }
}