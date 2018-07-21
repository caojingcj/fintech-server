package com.fintech.model.vo;

import java.math.BigDecimal;
import java.util.Date;

public class UserReturnplanVo extends BaseVO {
    private Integer id;

    private String orderId;

    private String contractId;

    private String custCellphone;

    private String companyId;

    private Integer currentPeriod;

    private Integer totalPeriod;

    private String returnStatus;

    private String returnChannel;

    private Date returnDate;

    private BigDecimal principalAmount;

    private BigDecimal interestAmount;

    private BigDecimal managementAmount;

    private Boolean isOverdue;

    private Integer overdueDays;

    private BigDecimal overdueAmount;

    private Date returnDateAc;

    private Date createTime;

    private Date updateTime;

    private String returnDateAcStart;
    private String returnDateAcEnd;
    private String returnDateStart;
    private String returnDateEnd;
    private String custRealname;
    private String companyName;

    public String getCustRealname() {
        return custRealname;
    }

    public void setCustRealname(String custRealname) {
        this.custRealname = custRealname;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getReturnDateAcStart() {
        return returnDateAcStart;
    }

    public void setReturnDateAcStart(String returnDateAcStart) {
        this.returnDateAcStart = returnDateAcStart;
    }

    public String getReturnDateAcEnd() {
        return returnDateAcEnd;
    }

    public void setReturnDateAcEnd(String returnDateAcEnd) {
        this.returnDateAcEnd = returnDateAcEnd;
    }

    public String getReturnDateStart() {
        return returnDateStart;
    }

    public void setReturnDateStart(String returnDateStart) {
        this.returnDateStart = returnDateStart;
    }

    public String getReturnDateEnd() {
        return returnDateEnd;
    }

    public void setReturnDateEnd(String returnDateEnd) {
        this.returnDateEnd = returnDateEnd;
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

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getCustCellphone() {
        return custCellphone;
    }

    public void setCustCellphone(String custCellphone) {
        this.custCellphone = custCellphone;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Integer getCurrentPeriod() {
        return currentPeriod;
    }

    public void setCurrentPeriod(Integer currentPeriod) {
        this.currentPeriod = currentPeriod;
    }

    public Integer getTotalPeriod() {
        return totalPeriod;
    }

    public void setTotalPeriod(Integer totalPeriod) {
        this.totalPeriod = totalPeriod;
    }

    public String getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(String returnStatus) {
        this.returnStatus = returnStatus;
    }

    public String getReturnChannel() {
        return returnChannel;
    }

    public void setReturnChannel(String returnChannel) {
        this.returnChannel = returnChannel;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public BigDecimal getPrincipalAmount() {
        return principalAmount;
    }

    public void setPrincipalAmount(BigDecimal principalAmount) {
        this.principalAmount = principalAmount;
    }

    public BigDecimal getInterestAmount() {
        return interestAmount;
    }

    public void setInterestAmount(BigDecimal interestAmount) {
        this.interestAmount = interestAmount;
    }

    public BigDecimal getManagementAmount() {
        return managementAmount;
    }

    public void setManagementAmount(BigDecimal managementAmount) {
        this.managementAmount = managementAmount;
    }

    public Boolean getIsOverdue() {
        return isOverdue;
    }

    public void setIsOverdue(Boolean isOverdue) {
        this.isOverdue = isOverdue;
    }

    public Integer getOverdueDays() {
        return overdueDays;
    }

    public void setOverdueDays(Integer overdueDays) {
        this.overdueDays = overdueDays;
    }

    public BigDecimal getOverdueAmount() {
        return overdueAmount;
    }

    public void setOverdueAmount(BigDecimal overdueAmount) {
        this.overdueAmount = overdueAmount;
    }

    public Date getReturnDateAc() {
        return returnDateAc;
    }

    public void setReturnDateAc(Date returnDateAc) {
        this.returnDateAc = returnDateAc;
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