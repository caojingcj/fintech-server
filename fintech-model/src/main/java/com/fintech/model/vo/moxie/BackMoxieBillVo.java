package com.fintech.model.vo.moxie;

import java.util.Arrays;

import com.fintech.model.vo.BaseVO;

public class BackMoxieBillVo extends BaseVO {
    private String mobile;
    private String user_id;
    private String task_id;
    private String[] bills;
    private String error_code;

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String[] getBills() {
        return bills;
    }

    public void setBills(String[] bills) {
        this.bills = bills;
    }

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

    @Override
    public String toString() {
        return "BackMoxieBillVo [mobile=" + mobile + ", user_id=" + user_id + ", task_id=" + task_id + ", bills="
                + Arrays.toString(bills) + ", error_code=" + error_code + "]";
    }

}
