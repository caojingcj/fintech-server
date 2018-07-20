package com.fintech.model.vo;

public class CompanyBaseinfoCombox {
	private String companyId;

	private String companyName;

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

	@Override
	public String toString() {
		return "CompanyBaseinfo [companyId=" + companyId + ", companyName=" + companyName + "]";
	}

}