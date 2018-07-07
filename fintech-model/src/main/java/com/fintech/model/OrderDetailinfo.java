package com.fintech.model;

import java.math.BigDecimal;
import java.util.Date;

public class OrderDetailinfo {
    private String orderId;

    private String maritalStatus;

    private String educationalStatus;

    private String vocationStatus;

    private Integer commAddrProvince;

    private Integer commAddrCity;

    private Integer commAddrDistrict;

    private String commAddr;

    private String contactName;

    private String contactPhone;

    private BigDecimal depositAmount;

    private Integer companyChannelId;

    private String companyChannelName;

    private String companyChannelPhone;

    private Date createTime;

    private Date updateTime;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getEducationalStatus() {
        return educationalStatus;
    }

    public void setEducationalStatus(String educationalStatus) {
        this.educationalStatus = educationalStatus;
    }

    public String getVocationStatus() {
        return vocationStatus;
    }

    public void setVocationStatus(String vocationStatus) {
        this.vocationStatus = vocationStatus;
    }

    public Integer getCommAddrProvince() {
        return commAddrProvince;
    }

    public void setCommAddrProvince(Integer commAddrProvince) {
        this.commAddrProvince = commAddrProvince;
    }

    public Integer getCommAddrCity() {
        return commAddrCity;
    }

    public void setCommAddrCity(Integer commAddrCity) {
        this.commAddrCity = commAddrCity;
    }

    public Integer getCommAddrDistrict() {
        return commAddrDistrict;
    }

    public void setCommAddrDistrict(Integer commAddrDistrict) {
        this.commAddrDistrict = commAddrDistrict;
    }

    public String getCommAddr() {
        return commAddr;
    }

    public void setCommAddr(String commAddr) {
        this.commAddr = commAddr;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public BigDecimal getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(BigDecimal depositAmount) {
        this.depositAmount = depositAmount;
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

    public String getCompanyChannelPhone() {
        return companyChannelPhone;
    }

    public void setCompanyChannelPhone(String companyChannelPhone) {
        this.companyChannelPhone = companyChannelPhone;
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

	/* (非 Javadoc) 
	* <p>Title: toString</p> 
	* <p>Description: </p> 
	* @return 
	* @see java.lang.Object#toString() 
	*/
	@Override
	public String toString() {
		return "OrderDetailinfo [orderId=" + orderId + ", maritalStatus=" + maritalStatus + ", educationalStatus="
				+ educationalStatus + ", vocationStatus=" + vocationStatus + ", commAddrProvince=" + commAddrProvince
				+ ", commAddrCity=" + commAddrCity + ", commAddrDistrict=" + commAddrDistrict + ", commAddr=" + commAddr
				+ ", contactName=" + contactName + ", contactPhone=" + contactPhone + ", depositAmount=" + depositAmount
				+ ", companyChannelId=" + companyChannelId + ", companyChannelName=" + companyChannelName
				+ ", companyChannelPhone=" + companyChannelPhone + ", createTime=" + createTime + ", updateTime="
				+ updateTime + "]";
	}
    
}