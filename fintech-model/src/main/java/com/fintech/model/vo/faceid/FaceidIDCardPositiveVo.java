package com.fintech.model.vo.faceid;

/**   
* @Title: OcrFaceidVo.java 
* @Package com.fintech.model.vo 
* @author qierkang xyqierkang@163.com   
* @date 2018年6月28日 上午3:16:09  
* @Description: TODO[ 身份证基本信息 ]
*/
public class FaceidIDCardPositiveVo {
    private birthday birthday;
    private String name;
    private String race;
    private String address;
    private String time_used;
    private String gender;
    private String request_id;
    private String id_card_number;
    private String side;
    private Legality legality;

    public Legality getLegality() {
        return legality;
    }

    public void setLegality(Legality legality) {
        this.legality = legality;
    }

    public birthday getBirthday() {
        return birthday;
    }

    public void setBirthday(birthday birthday) {
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime_used() {
        return time_used;
    }

    public void setTime_used(String time_used) {
        this.time_used = time_used;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getId_card_number() {
        return id_card_number;
    }

    public void setId_card_number(String id_card_number) {
        this.id_card_number = id_card_number;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

}

/**   
* @Title: OcrFaceidVo.java 
* @Package com.fintech.model.vo 
* @author qierkang xyqierkang@163.com   
* @date 2018年6月28日 上午3:15:53  
* @Description: TODO[ 出生年月 ]
*/
class birthday {
    private String year;
    private String day;
    private String month;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
