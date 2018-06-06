package com.fintech.model;

import java.util.Date;

public class MasterArea {
    private Integer areaId;

    private String areaName;

    private Integer parentAreaId;

    private String areaNameAbbr;

    private String areaLevel;

    private String cityCode;

    private String zipCode;

    private String areaFullName;

    private String areaLng;

    private String areaLat;

    private String areaPinyin;

    private Date createTime;

    private Date updatetime;

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getParentAreaId() {
        return parentAreaId;
    }

    public void setParentAreaId(Integer parentAreaId) {
        this.parentAreaId = parentAreaId;
    }

    public String getAreaNameAbbr() {
        return areaNameAbbr;
    }

    public void setAreaNameAbbr(String areaNameAbbr) {
        this.areaNameAbbr = areaNameAbbr;
    }

    public String getAreaLevel() {
        return areaLevel;
    }

    public void setAreaLevel(String areaLevel) {
        this.areaLevel = areaLevel;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAreaFullName() {
        return areaFullName;
    }

    public void setAreaFullName(String areaFullName) {
        this.areaFullName = areaFullName;
    }

    public String getAreaLng() {
        return areaLng;
    }

    public void setAreaLng(String areaLng) {
        this.areaLng = areaLng;
    }

    public String getAreaLat() {
        return areaLat;
    }

    public void setAreaLat(String areaLat) {
        this.areaLat = areaLat;
    }

    public String getAreaPinyin() {
        return areaPinyin;
    }

    public void setAreaPinyin(String areaPinyin) {
        this.areaPinyin = areaPinyin;
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