package com.fintech.model;

import java.util.Date;

public class CompanyItem {
    private Integer id;

    private String companyId;

    private String itemCode;

    private Date createTime;

    private Date updatetime;

    public CompanyItem() {
    }
    public CompanyItem(String companyId, String itemCode) {
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
}