package com.fintech.model.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by luoyangwei on 2017/5/27.
 */
public class ManageCompanyDo implements Serializable {

    private static final long serialVersionUID = 5814655927278932158L;

    private Integer id;
    private String companyId;
    private String companyName;
    private String companyCardName;
    private String companyAddress;
    private String companyCode;
    private String companyUrl;
    private String companyState;
    private Timestamp createTime;
    private String companyGroup;
    private String province;
    private String city;
    private String registeredMoney;
    private String idCard;
    private String phone;
    private String name;
    private String remark;
    private String companyChannel;
    private String companyDiscount;
    private String medicalDate;
    private String medicalBeauty;
    private String businessDate;
    private String pilotHospital;
    private String latitude;
    private String longitude;
    private String warningNum;
    private String warningState;
    private String phoneChannel;
    private String expandStaff;

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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyCardName() {
        return companyCardName;
    }

    public void setCompanyCardName(String companyCardName) {
        this.companyCardName = companyCardName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyUrl() {
        return companyUrl;
    }

    public void setCompanyUrl(String companyUrl) {
        this.companyUrl = companyUrl;
    }

    public String getCompanyState() {
        return companyState;
    }

    public void setCompanyState(String companyState) {
        this.companyState = companyState;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getCompanyGroup() {
        return companyGroup;
    }

    public void setCompanyGroup(String companyGroup) {
        this.companyGroup = companyGroup;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegisteredMoney() {
        return registeredMoney;
    }

    public void setRegisteredMoney(String registeredMoney) {
        this.registeredMoney = registeredMoney;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCompanyChannel() {
        return companyChannel;
    }

    public void setCompanyChannel(String companyChannel) {
        this.companyChannel = companyChannel;
    }

    public String getCompanyDiscount() {
        return companyDiscount;
    }

    public void setCompanyDiscount(String companyDiscount) {
        this.companyDiscount = companyDiscount;
    }

    public String getMedicalDate() {
        return medicalDate;
    }

    public void setMedicalDate(String medicalDate) {
        this.medicalDate = medicalDate;
    }

    public String getMedicalBeauty() {
        return medicalBeauty;
    }

    public void setMedicalBeauty(String medicalBeauty) {
        this.medicalBeauty = medicalBeauty;
    }

    public String getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(String businessDate) {
        this.businessDate = businessDate;
    }

    public String getPilotHospital() {
        return pilotHospital;
    }

    public void setPilotHospital(String pilotHospital) {
        this.pilotHospital = pilotHospital;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getWarningNum() {
        return warningNum;
    }

    public void setWarningNum(String warningNum) {
        this.warningNum = warningNum;
    }

    public String getWarningState() {
        return warningState;
    }

    public void setWarningState(String warningState) {
        this.warningState = warningState;
    }

    public String getPhoneChannel() {
        return phoneChannel;
    }

    public void setPhoneChannel(String phoneChannel) {
        this.phoneChannel = phoneChannel;
    }

    public String getExpandStaff() {
        return expandStaff;
    }

    public void setExpandStaff(String expandStaff) {
        this.expandStaff = expandStaff;
    }

	/* (非 Javadoc) 
	* <p>Title: toString</p> 
	* <p>Description: </p> 
	* @return 
	* @see java.lang.Object#toString() 
	*/
	@Override
	public String toString() {
		return "ManageCompanyDO [id=" + id + ", companyId=" + companyId + ", companyName=" + companyName
				+ ", companyCardName=" + companyCardName + ", companyAddress=" + companyAddress + ", companyCode="
				+ companyCode + ", companyUrl=" + companyUrl + ", companyState=" + companyState + ", createTime="
				+ createTime + ", companyGroup=" + companyGroup + ", province=" + province + ", city=" + city
				+ ", registeredMoney=" + registeredMoney + ", idCard=" + idCard + ", phone=" + phone + ", name=" + name
				+ ", remark=" + remark + ", companyChannel=" + companyChannel + ", companyDiscount=" + companyDiscount
				+ ", medicalDate=" + medicalDate + ", medicalBeauty=" + medicalBeauty + ", businessDate=" + businessDate
				+ ", pilotHospital=" + pilotHospital + ", latitude=" + latitude + ", longitude=" + longitude
				+ ", warningNum=" + warningNum + ", warningState=" + warningState + ", phoneChannel=" + phoneChannel
				+ ", expandStaff=" + expandStaff + "]";
	}

}
