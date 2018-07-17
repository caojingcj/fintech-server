package com.fintech.service;

import com.fintech.model.UserReturnplan;
import com.fintech.model.vo.UserReturnplanVo;
import com.github.pagehelper.PageInfo;

public interface UserReturnPlanService {
	PageInfo<UserReturnplan> selectByPrimaryKeyList(UserReturnplanVo vo) throws Exception;
}
