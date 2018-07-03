package com.fintech.service;

import com.fintech.model.vo.UserBaseinfoVo;

public interface UserBaseinfoService {
	UserBaseinfoVo manageLogin(String name, String pwd) throws Exception;
}
