package com.fintech.model.vo;

import java.math.BigDecimal;
import java.util.Date;

public class CompanyPeriodFeeVo extends BaseVO  {
    private Integer id;

    private String companyId;

    private Integer period;

    private BigDecimal rateTotal;

    private BigDecimal rateManage;

    private Date createTime;

    private Date updatetime;

    public CompanyPeriodFeeVo() {
    }
    public CompanyPeriodFeeVo(String companyId, Integer period, BigDecimal rateTotal, BigDecimal rateManage) {
        super();
        this.companyId = companyId;
        this.period = period;
        this.rateTotal = rateTotal;
        this.rateManage = rateManage;
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
	/* (非 Javadoc) 
	* <p>Title: toString</p> 
	* <p>Description: </p> 
	* @return 
	* @see java.lang.Object#toString() 
	*/
	@Override
	public String toString() {
		return "CompanyPeriodFeeVo [id=" + id + ", companyId=" + companyId + ", period=" + period + ", rateTotal="
				+ rateTotal + ", rateManage=" + rateManage + ", createTime=" + createTime + ", updatetime=" + updatetime
				+ "]";
	}
    
}