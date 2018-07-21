package com.fintech.service;

import com.fintech.model.domain.UserReturnplanDo;
import com.fintech.model.vo.UserReturnplanVo;
import com.github.pagehelper.PageInfo;

public interface UserReturnPlanService {
	PageInfo<UserReturnplanDo> selectByPrimaryKeyList(UserReturnplanVo vo) throws Exception;
}
