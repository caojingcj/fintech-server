package com.fintech.service;

import org.springframework.transaction.annotation.Transactional;

import com.fintech.model.LogMoxieinfo;
import com.fintech.model.vo.moxie.BackMoxieBillVo;
import com.fintech.model.vo.moxie.BackMoxieFailVo;
import com.fintech.model.vo.moxie.BackMoxieReportVo;
import com.fintech.model.vo.moxie.BackMoxieTaskSubmitVo;
import com.fintech.model.vo.moxie.BackMoxieTaskVo;

@Transactional(rollbackFor = Exception.class)
public interface MoxieService {

    void insertSelective(LogMoxieinfo record)throws Exception;

    void backMoxieTaskSubmit(BackMoxieTaskSubmitVo submitVo)throws Exception;

    void backMoxieTask(BackMoxieTaskVo vo)throws Exception;  

    void backMoxieFail(BackMoxieFailVo vo)throws Exception;  

    void backMoxieBill(BackMoxieBillVo vo) throws Exception;  

    void backMoxieReport(BackMoxieReportVo vo)throws Exception;  

}
