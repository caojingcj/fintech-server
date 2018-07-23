package com.fintech.model;

import java.util.Date;

public class CustBaseinfo {
    private String custCellphone;

    private String identityStatus;

    private String custRealname;

    private String custIdCardNo;

    private String custNation;

    private String custAddress;

    private Date custIdCardValBegin;

    private Date custIdCardValEnd;

    private String custIdCardFront;

    private String custIdCardBack;

    private Date identityTime;

    private Boolean isEnabled;

    private String custDeviceCode;

    private Date createTime;

    private Date updatetime;

    /** 
	* @Title: CustBaseinfo.java 
	* @param @param custCellphone
	* @param @param identityStatus
	* @param @param custRealname
	* @param @param custIdCardNo
	* @param @param custNation
	* @param @param custAddress
	* @param @param custIdCardValBegin
	* @param @param custIdCardValEnd
	* @param @param custIdCardFront
	* @param @param custIdCardBack
	* @param @param identityTime
	* @param @param isEnabled
	* @param @param custDeviceCode
	* @param @param createTime
	* @param @param updatetime    设定文件 
	* @Description: TODO[ 这里用一句话描述这个方法的作用 ]
	* @throws 
	*/
    public CustBaseinfo(String custCellphone, String identityStatus, String custRealname, String custIdCardNo,
			String custNation, String custAddress, Date custIdCardValBegin, Date custIdCardValEnd,
			String custIdCardFront, String custIdCardBack, Date identityTime, Boolean isEnabled, String custDeviceCode) {
		this.custCellphone = custCellphone;
		this.identityStatus = identityStatus;
		this.custRealname = custRealname;
		this.custIdCardNo = custIdCardNo;
		this.custNation = custNation;
		this.custAddress = custAddress;
		this.custIdCardValBegin = custIdCardValBegin;
		this.custIdCardValEnd = custIdCardValEnd;
		this.custIdCardFront = custIdCardFront;
		this.custIdCardBack = custIdCardBack;
		this.identityTime = identityTime;
		this.isEnabled = isEnabled;
		this.custDeviceCode = custDeviceCode;
	}
    
    public CustBaseinfo() {
	}

	public String getCustCellphone() {
        return custCellphone;
    }

    public void setCustCellphone(String custCellphone) {
        this.custCellphone = custCellphone;
    }

    public String getIdentityStatus() {
        return identityStatus;
    }

    public void setIdentityStatus(String identityStatus) {
        this.identityStatus = identityStatus;
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

    public String getCustNation() {
        return custNation;
    }

    public void setCustNation(String custNation) {
        this.custNation = custNation;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    public Date getCustIdCardValBegin() {
        return custIdCardValBegin;
    }

    public void setCustIdCardValBegin(Date custIdCardValBegin) {
        this.custIdCardValBegin = custIdCardValBegin;
    }

    public Date getCustIdCardValEnd() {
        return custIdCardValEnd;
    }

    public void setCustIdCardValEnd(Date custIdCardValEnd) {
        this.custIdCardValEnd = custIdCardValEnd;
    }

    public String getCustIdCardFront() {
        return custIdCardFront;
    }

    public void setCustIdCardFront(String custIdCardFront) {
        this.custIdCardFront = custIdCardFront;
    }

    public String getCustIdCardBack() {
        return custIdCardBack;
    }

    public void setCustIdCardBack(String custIdCardBack) {
        this.custIdCardBack = custIdCardBack;
    }

    public Date getIdentityTime() {
        return identityTime;
    }

    public void setIdentityTime(Date identityTime) {
        this.identityTime = identityTime;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public String getCustDeviceCode() {
        return custDeviceCode;
    }

    public void setCustDeviceCode(String custDeviceCode) {
        this.custDeviceCode = custDeviceCode;
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

    @Override
    public String toString() {
        return "CustBaseinfo [custCellphone=" + custCellphone + ", identityStatus=" + identityStatus + ", custRealname="
                + custRealname + ", custIdCardNo=" + custIdCardNo + ", custNation=" + custNation + ", custAddress="
                + custAddress + ", custIdCardValBegin=" + custIdCardValBegin + ", custIdCardValEnd=" + custIdCardValEnd
                + ", custIdCardFront=" + custIdCardFront + ", custIdCardBack=" + custIdCardBack + ", identityTime="
                + identityTime + ", isEnabled=" + isEnabled + ", custDeviceCode=" + custDeviceCode + ", createTime="
                + createTime + ", updatetime=" + updatetime + "]";
    }
    
}