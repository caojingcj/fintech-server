package com.fintech.model.vo;

import java.util.Date;

public class CompanyAccountinfoVo extends BaseVO {
    private Integer id;

    private String companyId;

    private String companyAccountName;

    private String companyAccountNo;

    private String companyAccountBranch;

    private String accountType;

    private Boolean isEnabled;

    private String bankCode;

    private Date createTime;

    private Date updatetime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyAccountName() {
        return companyAccountName;
    }

    public void setCompanyAccountName(String companyAccountName) {
        this.companyAccountName = companyAccountName;
    }

    public String getCompanyAccountNo() {
        return companyAccountNo;
    }

    public void setCompanyAccountNo(String companyAccountNo) {
        this.companyAccountNo = companyAccountNo;
    }

    public String getCompanyAccountBranch() {
        return companyAccountBranch;
    }

    public void setCompanyAccountBranch(String companyAccountBranch) {
        this.companyAccountBranch = companyAccountBranch;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
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

	/* (非 Javadoc) 
	* <p>Title: toString</p> 
	* <p>Description: </p> 
	* @return 
	* @see java.lang.Object#toString() 
	*/
	@Override
	public String toString() {
		return "CompanyAccountinfoVo [id=" + id + ", companyId=" + companyId + ", companyAccountName="
				+ companyAccountName + ", companyAccountNo=" + companyAccountNo + ", companyAccountBranch="
				+ companyAccountBranch + ", accountType=" + accountType + ", isEnabled=" + isEnabled + ", bankCode="
				+ bankCode + ", createTime=" + createTime + ", updatetime=" + updatetime + "]";
	}
    
}