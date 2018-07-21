package com.fintech.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.dao.UserReturnplanMapper;
import com.fintech.model.domain.UserReturnplanDo;
import com.fintech.model.vo.UserReturnplanVo;
import com.fintech.service.RedisService;
import com.fintech.service.UserReturnPlanService;
import com.fintech.util.CommonUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**   
* @Title: UserReturnPlanImpl.java 
* @Package com.fintech.service.impl 
* @author qierkang xyqierkang@163.com   
* @date 2018年7月17日 下午10:51:25  
* @Description: TODO[ 用一句话描述该文件做什么 ]
*/
@Service
public class UserReturnPlanImpl implements UserReturnPlanService {
	private static final Logger logger = LoggerFactory.getLogger(UserReturnPlanImpl.class);

	@Autowired
	private RedisService redisService;
	@Autowired
	private UserReturnplanMapper userReturnplanMapper;

	@Override
	public PageInfo<UserReturnplanDo> selectByPrimaryKeyList(UserReturnplanVo vo) throws Exception {
		 	Map<String, Object> parms = CommonUtil.object2Map(vo);
	        PageHelper.startPage(vo.getPageIndex(), vo.getPageSize());
	        List<UserReturnplanDo> list=userReturnplanMapper.selectByPrimaryKeyList(parms);
	        PageInfo<UserReturnplanDo> pageLists=new PageInfo<UserReturnplanDo>(list);
	        return pageLists;
	}

}
