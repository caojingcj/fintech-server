package com.fintech.model.vo;

import java.util.Date;

public class UserBaseinfoVo {
	private Integer id;

	private String userRealname;

	private String userLoginName;

	private String userLoginPassword;

	private String userType;

	private String userCompanyId;

	private Boolean isEnabled;

	private Date createTime;

	private Date updatetime;

	private String token;


	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserRealname() {
		return userRealname;
	}

	public void setUserRealname(String userRealname) {
		this.userRealname = userRealname;
	}

	public String getUserLoginName() {
		return userLoginName;
	}

	public void setUserLoginName(String userLoginName) {
		this.userLoginName = userLoginName;
	}

	public String getUserLoginPassword() {
		return userLoginPassword;
	}

	public void setUserLoginPassword(String userLoginPassword) {
		this.userLoginPassword = userLoginPassword;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserCompanyId() {
		return userCompanyId;
	}

	public void setUserCompanyId(String userCompanyId) {
		this.userCompanyId = userCompanyId;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
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