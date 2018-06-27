package com.fintech.model.vo.faceid;

/**   
* @Title: OcrFaceidVo.java 
* @Package com.fintech.model.vo 
* @author qierkang xyqierkang@163.com   
* @date 2018年6月28日 上午3:16:09  
* @Description: TODO[ 身份证基本信息 ]
*/
public class FaceidIDCardSideVo {
    private String time_used;
    private String valid_date;
    private String issued_by;
    private String side;
    private String request_id;
    public String getTime_used() {
        return time_used;
    }
    public void setTime_used(String time_used) {
        this.time_used = time_used;
    }
    public String getValid_date() {
        return valid_date;
    }
    public void setValid_date(String valid_date) {
        this.valid_date = valid_date;
    }
    public String getIssued_by() {
        return issued_by;
    }
    public void setIssued_by(String issued_by) {
        this.issued_by = issued_by;
    }
    public String getSide() {
        return side;
    }
    public void setSide(String side) {
        this.side = side;
    }
    public String getRequest_id() {
        return request_id;
    }
    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

}
