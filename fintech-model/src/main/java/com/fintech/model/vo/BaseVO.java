package com.fintech.model.vo;

public class BaseVO {

	private Integer pageIndex =1;

    private Integer pageSize = 10;

	private String beginDate;

	private String endDate;

	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public long getStartIndex() {
		return (pageIndex - 1) * pageSize;
	}

	@Override
	public String toString() {
		return "BaseVO [pageIndex=" + pageIndex + ", pageSize=" + pageSize + ", beginDate=" + beginDate + ", endDate="
				+ endDate + ", token=" + token + "]";
	}

}
