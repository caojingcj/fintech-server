package com.fintech.service;

import org.springframework.transaction.annotation.Transactional;

import com.fintech.model.UserBaseinfo;
import com.fintech.model.vo.UserBaseinfoVo;
import com.github.pagehelper.PageInfo;

@Transactional(rollbackFor = Exception.class)
public interface UserBaseinfoService {
	UserBaseinfoVo manageLogin(String name, String pwd) throws Exception;

	PageInfo<UserBaseinfo> selectByPrimaryKeyList(UserBaseinfoVo vo) throws Exception;

	void insertUserBaseInfo(UserBaseinfoVo vo);

	void updateUserBaseInfo(UserBaseinfoVo vo);
}
