package com.fintech.model;

import java.math.BigDecimal;
import java.util.Date;

public class CompanyPeriodFee {
    private Integer id;

    private String companyId;

    private Integer period;

    private BigDecimal rateTotal;

    private BigDecimal rateManage;

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

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public BigDecimal getRateTotal() {
        return rateTotal;
    }

    public void setRateTotal(BigDecimal rateTotal) {
        this.rateTotal = rateTotal;
    }

    public BigDecimal getRateManage() {
        return rateManage;
    }

    public void setRateManage(BigDecimal rateManage) {
        this.rateManage = rateManage;
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