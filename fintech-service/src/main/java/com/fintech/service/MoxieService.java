package com.fintech.service;

import org.springframework.transaction.annotation.Transactional;

import com.fintech.model.LogMoxieinfo;
import com.fintech.model.vo.moxie.BackMoxieTaskSubmitVo;
@Transactional(rollbackFor = Exception.class)
public interface MoxieService {
    boolean insertSelective(LogMoxieinfo record);
    
    void backMoxieTaskSubmit(BackMoxieTaskSubmitVo submitVo);
    
    String resultMoxie(String orderId)throws Exception;
}
