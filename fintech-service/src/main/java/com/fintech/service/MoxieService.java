package com.fintech.service;

import com.fintech.model.LogMoxieinfo;
import com.fintech.model.vo.moxie.BackMoxieTaskSubmitVo;

public interface MoxieService {
    void insertSelective(LogMoxieinfo record);
    
    void backMoxieTaskSubmit(BackMoxieTaskSubmitVo submitVo);
}
