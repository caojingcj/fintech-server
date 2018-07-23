package com.fintech.model.vo.moxie;

import com.fintech.model.vo.BaseVO;

public class BackMoxieTaskVo extends BaseVO {
    private String mobile;
    private String user_id;
    private String task_id;
    private long timestamp;
    private boolean result;
    private String message;

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

    @Override
    public String toString() {
        return "BackMoxieTaskVo [mobile=" + mobile + ", user_id=" + user_id + ", task_id=" + task_id + ", timestamp="
                + timestamp + ", result=" + result + ", message=" + message + "]";
    }

}
