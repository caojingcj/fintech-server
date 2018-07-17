package com.fintech.service;

import com.fintech.model.UserBaseinfo;
import com.fintech.model.vo.UserBaseinfoVo;
import com.github.pagehelper.PageInfo;

public interface UserBaseinfoService {
	UserBaseinfoVo manageLogin(String name, String pwd) throws Exception;
	
	PageInfo<UserBaseinfo> selectByPrimaryKeyList(UserBaseinfoVo vo) throws Exception;

}
