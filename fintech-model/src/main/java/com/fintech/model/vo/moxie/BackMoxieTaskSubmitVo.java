package com.fintech.model.vo.moxie;

import com.fintech.model.vo.BaseVO;

public class BackMoxieTaskSubmitVo extends BaseVO {
    private String mobile;
    private String user_id;
    private String task_id;
    private String name;
    private String idcard;
    private long timestamp;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
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

    @Override
    public String toString() {
        return "BackMoxieTaskSubmitVo [mobile=" + mobile + ", user_id=" + user_id + ", task_id=" + task_id + ", name="
                + name + ", idcard=" + idcard + ", timestamp=" + timestamp + "]";
    }

}
