package com.fintech.model.vo;

import java.util.Date;

public class CompanyItemVo extends BaseVO {
    private Integer id;

    private String companyId;

    private String itemCode;

    private Date createTime;

    private Date updatetime;

    public CompanyItemVo() {
    }
    public CompanyItemVo(String companyId, String itemCode) {
        super();
        this.companyId = companyId;
        this.itemCode = itemCode;
    }

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

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
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
		return "CompanyItemVo [id=" + id + ", companyId=" + companyId + ", itemCode=" + itemCode + ", createTime="
				+ createTime + ", updatetime=" + updatetime + "]";
	}
    
}