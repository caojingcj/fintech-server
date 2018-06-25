package com.fintech.model.vo;

import java.math.BigDecimal;
import java.util.Date;

public class OrderBaseinfoVo extends BaseVO {
    private String orderId;

    private String custCellphone;

    private String custRealname;

    private String custIdCardNo;

    private String orderStatus;

    private Boolean isAssure;

    private String companyId;

    private String companyName;

    private String itemCode;

    private String itemName;

    private Integer totalPeriod;

    private BigDecimal orderAmount;

    private String contractId;

    private Date createTime;

    private Date updatetime;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustCellphone() {
        return custCellphone;
    }

    public void setCustCellphone(String custCellphone) {
        this.custCellphone = custCellphone;
    }

    public String getCustRealname() {
        return custRealname;
    }

    public void setCustRealname(String custRealname) {
        this.custRealname = custRealname;
    }

    public String getCustIdCardNo() {
        return custIdCardNo;
    }

    public void setCustIdCardNo(String custIdCardNo) {
        this.custIdCardNo = custIdCardNo;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Boolean getIsAssure() {
        return isAssure;
    }

    public void setIsAssure(Boolean isAssure) {
        this.isAssure = isAssure;
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

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
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