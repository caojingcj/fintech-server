package com.fintech.model.vo.moxie;

import com.fintech.model.vo.BaseVO;

public class BackMoxieReportVo extends BaseVO {
    private String mobile;
    private String name;
    private String idcard;
    private long timestamp;
    private boolean result;
    private String message;
    private String task_id;
    private String user_id;
    private String type; //获取哪种报告?

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "BackMoxieReportVo [mobile=" + mobile + ", name=" + name + ", idcard=" + idcard + ", timestamp="
                + timestamp + ", result=" + result + ", message=" + message + ", task_id=" + task_id + ", user_id="
                + user_id + ", type=" + type + "]";
    }

}
