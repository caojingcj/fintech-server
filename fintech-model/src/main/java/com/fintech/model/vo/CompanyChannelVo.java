package com.fintech.model.vo;

import java.util.Date;

public class CompanyChannelVo extends BaseVO {
    private Integer id;

    private String companyId;

    private String channelName;

    private String channelPhone;

    private Date createTime;

    private Date updatetime;

    public CompanyChannelVo() {
    }

    public CompanyChannelVo(String companyId, String channelName, String channelPhone) {
        super();
        this.companyId = companyId;
        this.channelName = channelName;
        this.channelPhone = channelPhone;
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

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelPhone() {
        return channelPhone;
    }

    public void setChannelPhone(String channelPhone) {
        this.channelPhone = channelPhone;
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
        return "CompanyChannel [id=" + id + ", companyId=" + companyId + ", channelName=" + channelName
                + ", channelPhone=" + channelPhone + ", createTime=" + createTime + ", updatetime=" + updatetime + "]";
    }
}